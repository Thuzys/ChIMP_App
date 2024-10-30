package com.example.chimp.login.viewModel.state

import org.junit.Test

class RegisterTest {
    @Test
    fun isValidTest() {
        val register = Register.RegisterShow("username", "password")
        assert(register.isValid)
    }

    @Test
    fun isNotValidTest() {
        val register = Register.RegisterHide("", "password")
        assert(!register.isValid)
    }
}