package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLRootResolver
import graphql.GraphQLException
import graphql.schema.DataFetchingEnvironment






class Mutation(private val linkRepository: LinkRepository,
               private val userRepository: UserRepository ) : GraphQLRootResolver {

//    fun createLink(url: String, description: String): Link {
//        val newLink = Link(id="",url = url, description = description)
//        linkRepository.saveLink(newLink)
//        return newLink
//    }

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

    fun createLink(url: String, description: String, env: DataFetchingEnvironment): Link {
        val context = env.getContext<AuthContext>()
        val newLink = Link(url, description, context.user.id!!)
        linkRepository.saveLink(newLink)
        return newLink
    }
}