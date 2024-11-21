package com.example.chimp.screens.login.screen.view

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Login
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_INPUT_TAG
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_TRAILING_ICON_TAG
import org.junit.Rule
import org.junit.Test

class LoginViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testOnUsernameChange() {
        val text = "test"
        val pass = "pass"
        rule.setContent { LoginView(Login(password = pass)) }
        rule
            .onNodeWithText(text)
            .assertDoesNotExist()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .onFirst()
            .performTextInput(text)
        rule
            .onNodeWithText(text)
            .assertExists()
    }

    @Test
    fun onPasswordChange() {
        val text = "test"
        val user = "user"
        rule
            .setContent { LoginView(Login(username = user)) }
        rule
            .onNodeWithText(text)
            .assertDoesNotExist()
        rule
            .onNodeWithTag(MY_TEXT_FIELD_TRAILING_ICON_TAG)
            .performClick()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .onLast()
            .performTextInput(text)
        rule
            .onNodeWithText(text)
            .assertExists()
    }

    @Test
    fun onLoginChange() {
        var result = false
        val funTest: (String, String) -> Unit = { _, _ -> result = !result }
        rule
            .setContent {
                LoginView(
                    state = Login("valid", "V@lid123"),
                    onLoginChange = funTest
                )
            }
        rule
            .onNodeWithTag(LOGIN_VIEW_LOGIN_BUTTON)
            .performClick()
        assert(result)
    }

    @Test
    fun onLoginIsDisabled() {
        var result = false
        val funTest: (String, String) -> Unit = { _, _ -> result = !result }
        rule
            .setContent {
                LoginView(
                    state = Login(),
                    onLoginChange = funTest
                )
            }

        rule
            .onNodeWithTag(LOGIN_VIEW_LOGIN_BUTTON)
            .assertIsNotEnabled()

        rule
            .onNodeWithTag(LOGIN_VIEW_LOGIN_BUTTON)
            .performClick()
        assert(!result)
    }

    @Test
    fun onRegisterChange() {
        var result = false
        val funTest: (String, String) -> Unit = {_, _ -> result = !result }
        rule
            .setContent {
                LoginView(
                    state = Login(),
                    onRegisterChange = funTest
                )
            }
        rule
            .onNodeWithTag(LOGIN_VIEW_REGISTER_BUTTON)
            .performClick()
        assert(result)
    }
}