package com.example.chimp.models.users

import org.junit.Test

class UserTest {
    @Test
    fun create_a_valid_user() {
        User(1u, "Alice", Token("123"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_an_user_with_invalid_id_throws_an_exception() {
        User(0u, "Alice", Token("123"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_an_user_with_invalid_name_throws_an_exception() {
        User(1u, "", Token("123"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun create_an_user_with_invalid_token_throws_an_exception() {
        User(1u, "Alice", Token(""))
    }
}