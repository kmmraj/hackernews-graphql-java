package com.howtographql.hackernews

import com.coxautodev.graphql.tools.SchemaParser
import graphql.schema.GraphQLSchema
import graphql.servlet.SimpleGraphQLServlet
import javax.servlet.annotation.WebServlet


@WebServlet(urlPatterns = arrayOf("/graphql"))
class GraphQLEndpoint : SimpleGraphQLServlet(buildSchema()) {


    companion object {
        fun buildSchema(): GraphQLSchema {
            val linkRepository = LinkRepository()
            return SchemaParser.newParser()
                    .file("schema.graphqls")
                    .resolvers(Query(linkRepository))
                    .build()
                    .makeExecutableSchema()
        }
    }

}