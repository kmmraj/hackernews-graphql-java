package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLRootResolver
import graphql.GraphQLException




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

    @Throws(IllegalAccessException::class)
    fun signinUser(auth: AuthData): SigninPayload {
        auth.email?.let {
            val user = userRepository.findByEmail(it)
            if (user?.password == auth.password) {
                return SigninPayload(user?.id!!, user)
            }
        }
        throw GraphQLException("Invalid credentials")
    }
}