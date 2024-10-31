package com.example.chimp.login.viewModel.state

import org.junit.Test

class LoginTest {
    @Test
    fun isValidTest() {
        val login = Login.LoginShow("username", "password")
        assert(login.isValid)
    }

    @Test
    fun isNotValidTest() {
        val login = Login.LoginHide("", "password")
        assert(!login.isValid)
    }
}