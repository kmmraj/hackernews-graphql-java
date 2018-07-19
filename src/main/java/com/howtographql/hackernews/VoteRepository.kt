package com.howtographql.hackernews

import java.time.ZonedDateTime
import java.util.ArrayList
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.Document


class VoteRepository(private val votes: MongoCollection<Document>) {

    fun findByUserId(userId: String): List<Vote> {
        val list = ArrayList<Vote>()
        for (doc in votes.find(eq("userId", userId))) {
            list.add(vote(doc))
        }
        return list
    }

    fun findByLinkId(linkId: String): List<Vote> {
        val list = ArrayList<Vote>()
        for (doc in votes.find(eq("linkId", linkId))) {
            list.add(vote(doc))
        }
        return list
    }

    fun saveVote(vote: Vote): Vote {
        val doc = Document()
        doc.append("userId", vote.userId)
        doc.append("linkId", vote.linkId)
        doc.append("createdAt", Scalars.dateTime.coercing.serialize(vote.createdAt))
        votes.insertOne(doc)
        return Vote(
                doc.get("_id").toString(),
                vote.createdAt,
                vote.userId,
                vote.linkId)
    }

    private fun vote(doc: Document): Vote {
        return Vote(
                doc.get("_id").toString(),
                ZonedDateTime.parse(doc.getString("createdAt")),
                doc.getString("userId"),
                doc.getString("linkId")
        )
    }
}