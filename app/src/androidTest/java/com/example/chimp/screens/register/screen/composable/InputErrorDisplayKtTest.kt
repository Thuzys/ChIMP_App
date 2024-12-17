package com.example.chimp.screens.register.screen.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class InputErrorDisplayKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun error_is_displayed() {
        val error = "error"
        rule.setContent {
            InputErrorDisplay(message = error)
        }
        rule
            .onNodeWithText(
                text = error,
                useUnmergedTree = true
            )
            .assertExists()
    }
}