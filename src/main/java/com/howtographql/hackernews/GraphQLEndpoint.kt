package com.howtographql.hackernews

import com.coxautodev.graphql.tools.SchemaParser
import com.mongodb.MongoClient
import graphql.schema.GraphQLSchema
import graphql.servlet.GraphQLContext
import graphql.servlet.SimpleGraphQLServlet
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse




@WebServlet(urlPatterns = arrayOf("/graphql"))
class GraphQLEndpoint : SimpleGraphQLServlet(buildSchema()) {

    override fun createContext(request: Optional<HttpServletRequest>,
                               response: Optional<HttpServletResponse>): GraphQLContext {
        val user = request
                .map { req -> req.getHeader("Authorization") }
                .filter { id -> !id.isEmpty() }
                .map { id -> id.replace("Bearer ", "") }
                .map<User> { userRepository.findById(it) }
                .orElse(null)
        return AuthContext(user, request, response)
    }

//    override fun filterGraphQLErrors(errors: List<GraphQLError>): List<GraphQLError> {
//        return errors.stream()
//                .filter { e -> e is ExceptionWhileDataFetching || super.isClientError(e) }
//                .map { e -> if (e is ExceptionWhileDataFetching) SanitizedError(e) else e }
//                .collect<List<GraphQLError>, Any>(Collectors.toList())
//    }

    companion object {

        private val linkRepository: LinkRepository
        private val userRepository: UserRepository
        private val voteRepository: VoteRepository

        init {
            val mongo = MongoClient().getDatabase("hackernews")
            linkRepository = LinkRepository(mongo.getCollection("links"))
            userRepository = UserRepository(mongo.getCollection("users"))
            voteRepository = VoteRepository(mongo.getCollection("votes"))
        }

        private fun buildSchema(): GraphQLSchema {
            return SchemaParser.newParser()
                    .file("schema.graphqls")
                    .resolvers(
                            Query(linkRepository),
                            Mutation(linkRepository, userRepository, voteRepository),
                            SigninResolver(),
                            LinkResolver(userRepository),
                            VoteResolver(linkRepository, userRepository))
                    .scalars(Scalars.dateTime)
                    .build()
                    .makeExecutableSchema()
        }
    }
}