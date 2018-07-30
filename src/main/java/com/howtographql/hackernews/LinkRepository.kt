package com.howtographql.hackernews

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.*
import org.bson.Document
import org.bson.types.ObjectId
import org.bson.conversions.Bson
import java.util.*
import java.util.Optional.ofNullable




class LinkRepository(private val links: MongoCollection<Document>) {

    val allLinks: List<Link>
        get() {
            val allLinks = ArrayList<Link>()
            for (doc in links.find()) {
                allLinks.add(link(doc))
            }
            return allLinks
        }

    fun findById(id: String): Link {
        val doc = links.find(eq("_id", ObjectId(id))).first()
        return link(doc)
    }

    fun saveLink(link: Link) {
        val doc = Document()
        doc.append("url", link.url)
        doc.append("description", link.description)
        doc.append("postedBy",link.userId)
        links.insertOne(doc)
    }

    private fun link(doc: Document): Link {
        return Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"),
                doc.getString("postedBy")?:"")
    }

    fun getAllLinks(filter: LinkFilter): List<Link> {
        val mongoFilter = Optional.ofNullable(filter).map{this.buildFilter(it)}

        val allLinks = ArrayList<Link>()
        for (doc in mongoFilter.map{ links.find() }.orElseGet{ links.find() }) {
            allLinks.add(link(doc))
        }
        return allLinks
    }

    //builds a Bson from a LinkFilter
    private fun buildFilter(filter: LinkFilter): Bson? {
        val descriptionPattern = filter.descriptionContains
        val urlPattern = filter.urlContains
        var descriptionCondition: Bson? = null
        var urlCondition: Bson? = null
        if (descriptionPattern != null && !descriptionPattern.isEmpty()) {
            descriptionCondition = regex("description", ".*$descriptionPattern.*", "i")
        }
        if (urlPattern != null && !urlPattern.isEmpty()) {
            urlCondition = regex("url", ".*$urlPattern.*", "i")
        }
        return if (descriptionCondition != null && urlCondition != null) {
            and(descriptionCondition, urlCondition)
        } else descriptionCondition ?: urlCondition
    }
}