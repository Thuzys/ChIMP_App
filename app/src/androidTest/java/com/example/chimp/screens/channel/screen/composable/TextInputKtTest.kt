package com.example.chimp.screens.channel.screen.composable

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class TextInputKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun input_is_displayed() {
        rule.setContent {
            TextInput(
                onSendMessage = {}
            )
        }
        rule
            .onNodeWithTag(TEXT_INPUT_TAG)
            .assertExists()
    }

    @Test
    fun input_change_text() {
        rule.setContent {
            TextInput(
                onSendMessage = {}
            )
        }
        rule
            .onNodeWithTag(TEXT_INPUT_TAG)
            .performTextInput("Hello")

        rule
            .onNodeWithTag(TEXT_INPUT_TAG)
            .assert(hasText("Hello"))
    }

    @Test
    fun input_send_message() {
        var success = false
        val funcTest = { _:String -> success = true }
        rule.setContent {
            TextInput(
                onSendMessage = funcTest
            )
        }
        rule
            .onNodeWithTag(SEND_BUTTON_TAG)
            .performClick()

        assert(success)
    }
}