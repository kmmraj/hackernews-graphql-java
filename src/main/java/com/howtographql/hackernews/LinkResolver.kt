package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLResolver


class LinkResolver(private val userRepository: UserRepository) : GraphQLResolver<Link> {

    fun postedBy(link: Link): User? {
        return if (link.userId == null) {
            null
        } else userRepository.findById(link.userId)
    }
}