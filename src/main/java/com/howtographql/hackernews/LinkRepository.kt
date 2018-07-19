package com.howtographql.hackernews

import java.util.ArrayList
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.bson.types.ObjectId


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
}