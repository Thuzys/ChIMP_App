package com.example.chimp.about.model

import org.junit.Test

class DevTest {
    private val mail = Email("some@mail.com")
    @Test
    fun testDev() {
        Dev(
            name = "John Doe",
            number = "123456789",
            email = mail,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDevNameBlank() {
        Dev(
            name = "",
            number = "123456789",
            email = mail,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDevNumberBlank() {
        Dev(
            name = "John Doe",
            number = "",
            email = mail,
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDevBioBlank() {
        Dev(
            name = "John Doe",
            number = "123456789",
            email = mail,
            bio = "",
        )
    }
}