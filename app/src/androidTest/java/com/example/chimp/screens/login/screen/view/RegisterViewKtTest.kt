package com.example.chimp.screens.login.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Register
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_INPUT_TAG
import com.example.chimp.screens.ui.composable.MY_TEXT_FIELD_TRAILING_ICON_TAG
import org.junit.Rule
import org.junit.Test

class RegisterViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testOnUserChange() {
        val text = "test"
        val pass = "pass"
        rule.setContent { RegisterView(Register(password = pass)) }
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
    fun testOnPasswordChange() {
        val text = "test"
        val user = "user"
        rule.setContent { RegisterView(Register(username = user)) }
        rule
            .onNodeWithText(text)
            .assertDoesNotExist()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_TRAILING_ICON_TAG)
            .onFirst()
            .performClick()
        rule
            .onAllNodesWithTag(MY_TEXT_FIELD_INPUT_TAG)[1]
            .performTextInput(text)
        rule
            .onNodeWithText(text)
            .assertExists()
    }

    @Test
    fun testOnConfirmPasswordChange() {
        val pass = "pass"
        val user = "user"
        val text = "test"
        rule.setContent { RegisterView(Register(username = user, password = pass)) }
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
    fun testOnInvitationCodeChange() {
        val pass = "pass"
        val user = "user"
        val text = "test"
        rule.setContent { RegisterView(Register(username = user, password = pass)) }
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
    fun testOnRegisterChange() {
        var result = false
        val funTest: (String, String, String) -> Unit = {_, _, _ -> result = true }
        val username = "Valid"
        val password = "P@ssw0rd"
        val invitationCode = "123456"
        rule
            .setContent {
                RegisterView(
                    state = Register(username, password),
                    onRegisterChange = funTest
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
            .onNodeWithTag(REGISTER_VIEW_REGISTER_BUTTON_TEST_TAG)
            .performScrollTo()
            .performClick()
        assert(result)
    }

    @Test
    fun testOnRegisterIsNotEnabled() {
        var result = false
        val funTest: (String, String, String) -> Unit = {_, _, _ -> result = true }
        val username = "Valid"
        val password = ""
        rule
            .setContent {
                RegisterView(
                    state = Register(username, password),
                    onRegisterChange = funTest
                )
            }
        rule
            .onNodeWithTag(REGISTER_VIEW_REGISTER_BUTTON_TEST_TAG)
            .performScrollTo()
            .performClick()
        assert(!result)
    }

    @Test
    fun testOnLoginChange() {
        var result = false
        val funTest: (String, String) -> Unit = {_ ,_ -> result = true }
        rule
            .setContent {
                RegisterView(
                    state = Register(),
                    onLoginChange = funTest
                )
            }
        rule
            .onNodeWithTag(REGISTER_VIEW_LOGIN_BUTTON_TEST_TAG)
            .performScrollTo()
            .performClick()
        assert(result)
    }
}