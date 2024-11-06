package com.example.chimp.model

import com.example.chimp.model.users.User
import org.junit.Test

class UserTest {
    @Test
    fun testUser() {
        User(1u, "Alice", "123")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUserWithInvalidId() {
        User(0u, "Alice", "123")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUserWithInvalidName() {
        User(1u, "", "123")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testUserWithInvalidToken() {
        User(1u, "Alice", "")
    }
}