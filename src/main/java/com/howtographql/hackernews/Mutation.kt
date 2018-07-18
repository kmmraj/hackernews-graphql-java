package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLRootResolver


class Mutation(private val linkRepository: LinkRepository,
               private val userRepository: UserRepository ) : GraphQLRootResolver {

    fun createLink(url: String, description: String): Link {
        val newLink = Link(id="",url = url, description = description)
        linkRepository.saveLink(newLink)
        return newLink
    }

    fun createUser(name: String, auth: AuthData): User {
        val newUser = User(name, auth.email!!, auth.password!!)
        return userRepository.saveUser(newUser)
    }
}