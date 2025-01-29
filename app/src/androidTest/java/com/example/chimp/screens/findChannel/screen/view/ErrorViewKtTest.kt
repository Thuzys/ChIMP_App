package com.example.chimp.screens.findChannel.screen.view

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import org.junit.Rule
import org.junit.Test

class ErrorViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun error_message_is_displayed() {
        rule.setContent {
            ErrorView(
                state = FindChannelScreenState.Error(
                    error = ResponseError(
                        cause = "Error message"
                    ),
                    goBack = FindChannelScreenState.Initial
                ),
                close = {}
            )
        }

        rule
            .onNodeWithTag("error_message")
            .assertTextEquals("Error message")
    }

    @Test
    fun clicking_close_button_calls_goBack_function() {
        var closeCalled = false
        rule.setContent {
            ErrorView(
                state = FindChannelScreenState.Error(
                    error = ResponseError(
                        cause = "Error message"
                    ),
                    goBack = FindChannelScreenState.Initial
                ),
                close = { closeCalled = true }
            )
        }

        rule
            .onNodeWithTag("close_button")
            .performClick()

        assert(closeCalled)
    }

}