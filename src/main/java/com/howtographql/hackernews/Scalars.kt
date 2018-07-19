package com.howtographql.hackernews

import graphql.language.StringValue
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType


object Scalars {

    var dateTime = GraphQLScalarType("DateTime", "DataTime scalar", object : Coercing<Any,Any> {
        override fun serialize(input: Any): String {
            //serialize the ZonedDateTime into string on the way out
            return (input as ZonedDateTime).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        override fun parseValue(input: Any): Any {
            return serialize(input)
        }

        override fun parseLiteral(input: Any): ZonedDateTime? {
            //parse the string values coming in
            return if (input is StringValue) {
                ZonedDateTime.parse((input as StringValue).getValue())
            } else {
                null
            }
        }
    })
}