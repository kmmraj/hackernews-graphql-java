package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLRootResolver


class Mutation(private val linkRepository: LinkRepository) : GraphQLRootResolver {

    fun createLink(url: String, description: String): Link {
        val newLink = Link(id="",url = url, description = description)
        linkRepository.saveLink(newLink)
        return newLink
    }
}