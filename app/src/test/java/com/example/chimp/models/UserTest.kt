package com.example.chimp.models

import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import org.junit.Test

class UserTest {
    @Test
    fun testUser() {
        User(1u, "Alice", Token("123"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUserWithInvalidId() {
        User(0u, "Alice", Token("123"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUserWithInvalidName() {
        User(1u, "", Token("123"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUserWithInvalidToken() {
        User(1u, "Alice", Token(""))
    }
}