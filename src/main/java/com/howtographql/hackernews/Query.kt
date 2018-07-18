package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLRootResolver

class Query(private val linkRepository: LinkRepository) : GraphQLRootResolver {

    fun allLinks(): List<Link> {
        return linkRepository.allLinks
    }
}