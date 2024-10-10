package com.example.chimp.model.dev

import org.junit.Test

class EmailTest {

    @Test
    fun `successful Email instantiation`() {
        val email = "test@gmail.com"
        Email(email)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `unsuccessful Email instantiation due to invalid email`() {
        val email = "test@gmail"
        Email(email)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `unsuccessful Email instantiation due to blank email`() {
        val email = ""
        Email(email)
    }
}