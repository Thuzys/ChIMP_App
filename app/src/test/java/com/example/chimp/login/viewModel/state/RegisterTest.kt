package com.example.chimp.login.viewModel.state

import org.junit.Test

class RegisterTest {
    @Test
    fun isValidTest() {
        val register = _root_ide_package_.com.example.chimp.screens.login.viewModel.state.Register.RegisterShow("username", "password")
        assert(register.isValid)
    }

    @Test
    fun isNotValidTest() {
        val register = _root_ide_package_.com.example.chimp.screens.login.viewModel.state.Register.RegisterHide("", "password")
        assert(!register.isValid)
    }
}