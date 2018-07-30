package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLRootResolver

class Query(private val linkRepository: LinkRepository) : GraphQLRootResolver {

    fun allLinks(): List<Link> {
        return linkRepository.allLinks
    }

    fun allLinks(filter: LinkFilter?): List<Link> {
        filter?.let { return linkRepository.getAllLinks(filter) }
        return allLinks()
    }

    fun allLinks(filter: LinkFilter, skip: Number, first: Number): List<Link> {
        return linkRepository.getAllLinks(filter, skip.toInt(), first.toInt())
    }
}