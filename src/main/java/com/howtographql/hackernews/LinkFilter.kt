package com.howtographql.hackernews

import com.fasterxml.jackson.annotation.JsonProperty

class LinkFilter {

    @get:JsonProperty("description_contains") //the name must match the schema
    var descriptionContains: String? = null
    @get:JsonProperty("url_contains")
    var urlContains: String? = null
}