package com.example.chimp.models.users

import org.junit.Test

class UserTest {

    private val token = Token("123")
    private val emptyToken = Token("")
    private val name = "Alice"
    private val emptyName = ""
    private val id = 1u
    private val invalidId = 0u

    @Test
    fun create_a_valid_user() {
        User(id, "Alice", token)
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_an_user_with_invalid_id_throws_an_exception() {
        User(invalidId, name, token)
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_an_user_with_invalid_name_throws_an_exception() {
        User(id, emptyName, token)
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_an_user_with_invalid_token_throws_an_exception() {
        User(id, name, emptyToken)
    }
}