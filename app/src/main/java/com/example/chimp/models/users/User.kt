package com.example.chimp.models.users

/**
 * User is a data class that represents a user.
 *
 * @property id the user's id
 * @property name the user's name
 * @property token the user's token
 */
data class User(
    val id: UInt,
    val name: String,
    val token: String
) {
    init {
        require(id > 0u) { "id must be greater than 0" }
        require(name.isNotBlank()) { "name must not be blank" }
        require(token.isNotBlank()) { "token must not be blank" }
    }
}
