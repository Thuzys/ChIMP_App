package com.example.chimp.screens.login.viewModel.state

import org.junit.Test

class RegisterTest {
    @Test
    fun isValidTest() {
        val register = Register
            .RegisterShow(
                username = "username",
                password = "P@ssw0rd",
                confirmPassword = "P@ssw0rd",
                invitationCode = "123456"
            )
        assert(register.isValid)
    }

    @Test
    fun isNotValidTest() {
        val register = Register.RegisterHide("", "password")
        assert(!register.isValid)
    }
}