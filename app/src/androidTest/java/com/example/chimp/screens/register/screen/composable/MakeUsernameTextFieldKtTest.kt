package com.example.chimp.screens.register.screen.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class MakeUsernameTextFieldKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun username_is_displayed() {
        val value = "Hello"
        rule.setContent {
            MakeUsernameTextField(
                value = value,
                onUsernameChange = {}
            )
        }
        rule
            .onNodeWithText(
                text = value,
                useUnmergedTree = true
            ).assertExists()
    }

    @Test
    fun on_username_change() {
        val value = "Hello"
        val change = "change"
        var result = ""
        val expected = change+value
        val funcTest: (String) -> Unit = { result = it }
        rule.setContent {
            MakeUsernameTextField(
                value = value,
                onUsernameChange = funcTest
            )
        }
        rule
            .onNodeWithText(
                text = value,
                useUnmergedTree = true
            ).performTextInput(change)
        assert(result == expected)
    }
}