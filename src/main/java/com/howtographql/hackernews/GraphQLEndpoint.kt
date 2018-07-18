package com.howtographql.hackernews

import com.coxautodev.graphql.tools.SchemaParser
import graphql.schema.GraphQLSchema
import graphql.servlet.SimpleGraphQLServlet
import javax.servlet.annotation.WebServlet
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase




@WebServlet(urlPatterns = arrayOf("/graphql"))
class GraphQLEndpoint : SimpleGraphQLServlet(buildSchema()) {

    init {
        //Change to `new MongoClient("mongodb://<host>:<port>/hackernews")`
        //if you don't have Mongo running locally on port 27017

    }


    companion object {
        fun buildSchema(): GraphQLSchema {
            val mongo =  MongoClient().getDatabase("hackernews")
            val linkRepository =   LinkRepository(mongo.getCollection("links"))

            val userRepository = UserRepository(mongo.getCollection("users"))


            return SchemaParser.newParser()
                    .file("schema.graphqls")
                    .resolvers(Query(linkRepository),
                            Mutation(linkRepository,userRepository))
                    .build()
                    .makeExecutableSchema()
        }
    }

}