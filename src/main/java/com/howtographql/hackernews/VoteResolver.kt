package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLResolver


class VoteResolver(private val linkRepository: LinkRepository,
                   private val userRepository: UserRepository) : GraphQLResolver<Vote> {

    fun user(vote: Vote): User? {
        return userRepository.findById(vote.userId)
    }

    fun link(vote: Vote): Link {
        return linkRepository.findById(vote.linkId)
    }
}