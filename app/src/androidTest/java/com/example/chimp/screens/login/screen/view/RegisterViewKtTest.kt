package com.example.chimp.screens.login.screen.view

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.chimp.R
import com.example.chimp.screens.login.viewModel.state.Register
import org.junit.Rule
import org.junit.Test

class RegisterViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testOnUserChange() {
        val username = "username"
        val change = "change"
        val expected = change + username
        var result = ""
        val funTest: (String) -> Unit = { result = it }
        rule
            .setContent {
                RegisterView(
                    vm = Register.RegisterShow(
                        username = username,
                        password = "",
                        confirmPassword = ""
                    ),
                    onUsernameChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = username,
                useUnmergedTree = true
            )
            .performTextInput(change)
        assert(result == expected)
    }

    @Test
    fun testOnPasswordChange() {
        val password = "password"
        val change = "change"
        val expected = change + password
        var result = ""
        val funTest: (String) -> Unit = { result = it }
        rule
            .setContent {
                RegisterView(
                    vm = Register.RegisterShow(
                        username = "",
                        password = password,
                        confirmPassword = ""
                    ),
                    onPasswordChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = password,
                useUnmergedTree = true
            )
            .performTextInput(change)
        assert(result == expected)
    }

    @Test
    fun testOnConfirmPasswordChange() {
        val confirmPassword = "confirmPassword"
        val change = "change"
        val expected = change + confirmPassword
        var result = ""
        val funTest: (String) -> Unit = { result = it }
        rule
            .setContent {
                RegisterView(
                    vm = Register.RegisterShow("", "", confirmPassword),
                    onConfirmPasswordChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = confirmPassword,
                useUnmergedTree = true
            )
            .performTextInput(change)
        assert(result == expected)
    }

    @Test
    fun testOnInvitationCodeChange() {
        val invitationCode = "invitationCode"
        val change = "change"
        val expected = change + invitationCode
        var result = ""
        val funTest: (String) -> Unit = { result = it }
        rule
            .setContent {
                RegisterView(
                    vm = Register.RegisterShow(
                        username = "",
                        password = "",
                        confirmPassword = "",
                        invitationCode = invitationCode
                    ),
                    onInvitationCodeChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = invitationCode,
                useUnmergedTree = true
            )
            .performTextInput(change)
        assert(result == expected)
    }

    @Test
    fun testOnRegisterChange() {
        var result = false
        val funTest: () -> Unit = { result = true }
        val username = "Valid"
        val password = "P@ssw0rd"
        val invitationCode = "123456"
        var register = ""
        rule
            .setContent {
                register = stringResource(R.string.register)
                RegisterView(
                    vm = Register.RegisterShow(
                        username = username,
                        password = password,
                        confirmPassword = password,
                        invitationCode = invitationCode
                    ),
                    onRegisterChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = register,
                useUnmergedTree = true
            )
            .performScrollTo()
            .performClick()
        assert(result)
    }

    @Test
    fun testOnRegisterIsNotEnabled() {
        var result = false
        val funTest: () -> Unit = { result = true }
        val username = "Valid"
        val password = ""
        val invitationCode = "123456"
        var register = ""
        rule
            .setContent {
                register = stringResource(R.string.register)
                RegisterView(
                    vm = Register.RegisterShow(
                        username = username,
                        password = password,
                        confirmPassword = password,
                        invitationCode = invitationCode
                    ),
                    onRegisterChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = register,
                useUnmergedTree = true
            )
            .performScrollTo()
            .performClick()
        assert(!result)
    }

    @Test
    fun testOnLoginChange() {
        var result = false
        val funTest: () -> Unit = { result = true }
        var login = ""
        rule
            .setContent {
                login = stringResource(R.string.login)
                RegisterView(
                    vm = Register.RegisterShow("", "", ""),
                    onLoginChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = login,
                useUnmergedTree = true
            )
            .performScrollTo()
            .performClick()
        assert(result)
    }
}