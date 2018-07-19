package com.howtographql.hackernews

import java.time.ZonedDateTime


class Vote(val id: String?,
           val createdAt: ZonedDateTime,
           val userId: String,
           val linkId: String) {

    constructor(createdAt: ZonedDateTime,
                userId: String,
                linkId: String) : this(null, createdAt, userId, linkId) {}
}