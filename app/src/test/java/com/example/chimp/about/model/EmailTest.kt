package com.example.chimp.about.model

import org.junit.Test


class EmailTest {
    @Test
    fun mailIsValid() {
        Email("valid@mail.com")
    }

    @Test(expected = IllegalArgumentException::class)
    fun mailIsInvalid() {
        Email("invalidMail.com")
    }
}