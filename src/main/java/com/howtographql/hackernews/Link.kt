package com.howtographql.hackernews

class Link(val id: String? //the new field
           , val url: String, val description: String) {

    constructor(url: String, description: String) : this(null, url, description)

}