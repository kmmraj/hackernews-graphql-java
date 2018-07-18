package com.howtographql.hackernews

import com.coxautodev.graphql.tools.GraphQLResolver


class SigninResolver : GraphQLResolver<SigninPayload> {

    fun user(payload: SigninPayload): User {
        return payload.user
    }
}