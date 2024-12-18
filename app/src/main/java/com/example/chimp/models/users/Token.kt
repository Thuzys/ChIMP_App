package com.example.chimp.models.users

import java.sql.Timestamp

data class Token(
    val token: String,
    val expirationDate: Timestamp = Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
) {
    init {
        require(token.isNotBlank()) { "token must not be blank" }
    }
}
