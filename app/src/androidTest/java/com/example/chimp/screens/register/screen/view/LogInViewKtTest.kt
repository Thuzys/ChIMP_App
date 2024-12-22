package com.example.chimp.screens.register.screen.view

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.chimp.models.either.success
import com.example.chimp.models.DataInput
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.LogIn
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_INPUT_TAG
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_TRAILING_ICON_TAG
import org.junit.Rule
import org.junit.Test

class LogInViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun on_username_change() {
        val text = "test"
        val pass = "pass"
        var success = false
        val testFunc: (String) -> Unit = { success = true }
        rule.setContent {
            LoginView(
                LogIn(password = DataInput.fromString(pass)),
                onUsernameChange = testFunc
            )
        }
        rule
            .onNodeWithText(text)
            .assertDoesNotExist()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .onFirst()
            .performTextInput(text)
        assert(success)
    }

    @Test
    fun on_password_change() {
        val text = "test"
        val user = "user"
        var success = false
        val testFunc: (String) -> Unit = { success = true }
        rule
            .setContent {
                LoginView(
                    LogIn(username = DataInput.fromString(user)),
                    onPasswordChange = testFunc
                )
            }
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
        assert(success)
    }

    @Test
    fun on_login_click() {
        var success = false
        val funTest: (String, String) -> Unit = { _, _ -> success = true }
        rule
            .setContent {
                LoginView(
                    state = LogIn(
                        DataInput("valid", success(true)),
                        DataInput("V@lid123", success(true))
                    ),
                    onLogin = funTest
                )
            }
        rule
            .onNodeWithTag(LOGIN_VIEW_LOGIN_BUTTON)
            .performClick()
        assert(success)
    }

    @Test
    fun on_login_is_disable_when_username_and_password_are_blank() {
        var success = true
        val funTest: (String, String) -> Unit = { _, _ -> success = false }
        rule
            .setContent {
                LoginView(
                    state = LogIn(),
                    onLogin = funTest
                )
            }

        rule
            .onNodeWithTag(LOGIN_VIEW_LOGIN_BUTTON)
            .assertIsNotEnabled()

        rule
            .onNodeWithTag(LOGIN_VIEW_LOGIN_BUTTON)
            .performClick()
        assert(success)
    }

    @Test
    fun on_register_click() {
        var success = false
        val funTest: () -> Unit = { success = true }
        rule
            .setContent {
                LoginView(
                    state = LogIn(),
                    onRegisterClick = funTest
                )
            }
        rule
            .onNodeWithTag(LOGIN_VIEW_REGISTER_BUTTON)
            .performClick()
        assert(success)
    }
}