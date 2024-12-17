package com.example.chimp.screens.register.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.screens.register.model.DataInput
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Register
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_INPUT_TAG
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_TRAILING_ICON_TAG
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class RegisterViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun on_username_change() {
        val text = "test"
        val pass = "pass"
        var success = false
        val funcTest: (String) -> Unit = { success = true }
        rule.setContent {
            RegisterView(
                Register(password = DataInput.fromString(pass)),
                onUsernameChange = funcTest
            )
        }
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
        val funcTest: (String) -> Unit = { success = true }
        rule.setContent {
            RegisterView(
                Register(username = DataInput.fromString(user)),
                onPasswordChange = funcTest
            )
        }
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)[1]
            .performTextInput(text)
        assert(success)
    }

    @Test
    fun on_confirm_password_change() {
        val text = "test"
        rule.setContent { RegisterView(Register()) }
        rule
            .onNodeWithText(text)
            .assertDoesNotExist()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_TRAILING_ICON_TAG)
            .onLast()
            .performClick()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)[2]
            .performTextInput(text)
        rule
            .onNodeWithText(text)
            .assertExists()
    }

    @Test
    fun on_invitation_code_change() {
        val text = "test"
        rule.setContent { RegisterView(Register()) }
        rule
            .onNodeWithText(text)
            .assertDoesNotExist()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .onLast()
            .performTextInput(text)
        rule
            .onNodeWithText(text)
            .assertExists()
    }

    @Test
    fun on_register_click() {
        var result = false
        val funTest: (String, String, String) -> Unit = {_, _, _ -> result = true }
        val username = "Valid"
        val password = "P@ssw0rd"
        val invitationCode = "123456"
        rule
            .setContent {
                RegisterView(
                    state = Register(
                        DataInput(username, success(true)),
                        DataInput(password, success(true)),
                    ),
                    onRegister = funTest
                )
            }
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .onLast()
            .performTextInput(invitationCode)
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)[2]
            .performTextInput(password)
        rule
            .onNodeWithTag(
                testTag = REGISTER_VIEW_REGISTER_BUTTON_TEST_TAG,
                useUnmergedTree = true
            )
            .performScrollTo()
            .performClick()
        assert(result)
    }

    @Test
    fun register_button_is_not_enable() {
        var success = true
        val funTest: (String, String, String) -> Unit = {_, _, _ -> success = false}
        val username = "Valid"
        val password = ""
        rule
            .setContent {
                RegisterView(
                    state = Register(
                        DataInput(username, failure("")),
                        DataInput(password, failure("")),
                    ),
                    onRegister = funTest
                )
            }
        rule
            .onNodeWithTag(REGISTER_VIEW_REGISTER_BUTTON_TEST_TAG)
            .performScrollTo()
            .performClick()
        assert(success)
    }

    @Test
    fun on_login_click() {
        var success = false
        val funTest: () -> Unit = { success = true }
        rule
            .setContent {
                RegisterView(
                    state = Register(),
                    onLoginClick = funTest
                )
            }
        rule
            .onNodeWithTag(REGISTER_VIEW_LOGIN_BUTTON_TEST_TAG)
            .performScrollTo()
            .performClick()
        assert(success)
    }
}