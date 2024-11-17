package com.example.chimp.screens.login.screen.view

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.chimp.R
import com.example.chimp.screens.login.viewModel.state.Login
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_TRAILING_ICON_TAG
import org.junit.Rule
import org.junit.Test

class LoginViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testOnUsernameChange() {
        val username = "username"
        val change = "change"
        val expected = change+username
        var result = ""
        val funTest: (String) -> Unit = { result = it }
        rule
            .setContent {
                LoginView(
                    vm = Login.LoginShow(username, ""),
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
    fun onPasswordChange() {
        val password = "password"
        val change = "change"
        val expected = change+password
        var result = ""
        val funTest: (String) -> Unit = { result = it }
        rule
            .setContent {
                LoginView(
                    vm = Login.LoginShow("", password),
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
    fun isToShowChange() {
        var result = false
        val funTest: () -> Unit = { result = !result }
        rule
            .setContent {
                LoginView(
                    vm = Login.LoginShow("", ""),
                    isToShowChange = funTest
                )
            }
        rule
            .onNodeWithTag(
                testTag = MY_TEXT_FIELD_TRAILING_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()
        assert(result)
    }

    @Test
    fun onLoginChange() {
        var result = false
        val funTest: () -> Unit = { result = !result }
        var login = ""
        rule
            .setContent {
                login = stringResource(R.string.login)
                LoginView(
                    vm = Login.LoginShow("valid", "V@lid123"),
                    onLoginChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = login,
                useUnmergedTree = true
            )
            .performClick()
        assert(result)
    }

    @Test
    fun onLoginIsDisabled() {
        var result = false
        val funTest: () -> Unit = { result = !result }
        var login = ""
        rule
            .setContent {
                login = stringResource(R.string.login)
                LoginView(
                    vm = Login.LoginShow("", ""),
                    onLoginChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = login,
                useUnmergedTree = true
            )
            .performClick()
            //.assertIsNotEnabled() TODO: Ask about this line
        assert(!result)
    }

    @Test
    fun onRegisterChange() {
        var result = false
        val funTest: () -> Unit = { result = !result }
        var register = ""
        rule
            .setContent {
                register = stringResource(R.string.register)
                LoginView(
                    vm = Login.LoginShow("", ""),
                    onRegisterChange = funTest
                )
            }
        rule
            .onNodeWithText(
                text = register,
                useUnmergedTree = true
            )
            .performClick()
        assert(result)
    }
}