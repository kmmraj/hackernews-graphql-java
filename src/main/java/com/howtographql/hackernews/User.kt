package com.howtographql.hackernews

class User(val id: String?, val name: String, val email: String, val password: String) {

    constructor(name: String, email: String, password: String) : this(null, name, email, password) {}
}