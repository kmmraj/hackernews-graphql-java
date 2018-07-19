package com.howtographql.hackernews

class Link(val id: String?, val url: String, val description: String, val userId: String) {

    constructor(url: String, description: String, userId: String) : this(null, url, description, userId)
}