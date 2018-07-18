package com.howtographql.hackernews

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.bson.types.ObjectId


class UserRepository(private val users: MongoCollection<Document>) {

    fun findByEmail(email: String): User? {
        val doc = users.find(eq("email", email)).first()
        return user(doc)
    }

    fun findById(id: String): User? {
        val doc = users.find(eq("_id", ObjectId(id))).first()
        return user(doc)
    }

    fun saveUser(user: User): User {
        val doc = Document()
        doc.append("name", user.name)
        doc.append("email", user.email)
        doc.append("password", user.password)
        users.insertOne(doc)
        return User(
                doc.get("_id").toString(),
                user.name,
                user.email,
                user.password)
    }

    private fun user(doc: Document?): User? {
        return if (doc == null) {
            null
        } else User(
                doc.get("_id").toString(),
                doc.getString("name"),
                doc.getString("email"),
                doc.getString("password"))
    }
}