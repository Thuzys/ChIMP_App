package com.example.chimp.screens.ui.views

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chimp.models.errors.ResponseError
import org.junit.Rule
import org.junit.Test

class ErrorViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun error_text_is_displayed() {
        val errorMsg = "Error message"
        val error = ResponseError(errorMsg, "error_code")
        rule.setContent {
            ErrorView(
                modifier = Modifier,
                errors = error
            )
        }

        rule
            .onNodeWithText(errorMsg)
            .assertExists()
    }

    @Test
    fun try_again_button_is_clicked() {
        val errorMsg = "Error message"
        val error = ResponseError(errorMsg, "error_code")
        var success = false
        val funTest: () -> Unit = { success = true }
        rule.setContent {
            ErrorView(
                modifier = Modifier,
                errors = error,
                tryAgain = funTest
            )
        }

        rule
            .onNodeWithTag(testTag = ERROR_BUTTON_TEST_TAG)
            .performClick()
        assert(success)
    }
}