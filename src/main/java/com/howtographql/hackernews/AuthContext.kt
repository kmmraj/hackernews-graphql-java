package com.howtographql.hackernews

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import graphql.servlet.GraphQLContext
import java.util.*


class AuthContext(val user: User, request: Optional<HttpServletRequest>, response: Optional<HttpServletResponse>) :
        GraphQLContext(request, response)