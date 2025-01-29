package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test


class SelectOutlinedTextFieldKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun dropdown_menu_is_displayed_when_arrow_down_is_clicked() {
        val options = listOf("Option 1", "Option 2", "Option 3")
        var selectedOption = options[0]
        rule.setContent {
            SelectOutlinedTextField(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it },
                label = "Select Option"
            )
        }

        rule.onNodeWithTag(SELECT_OUTLINED_TEXT_FIELD_ARROW_DOWN_TAG)
            .performClick()

        rule.onNodeWithText("Option 2")
            .assertExists()
        rule.onNodeWithText("Option 3")
            .assertExists()
    }

    @Test
    fun option_is_selected_when_clicked() {
        val options = listOf("Option 1", "Option 2", "Option 3")
        var selectedOption = options[0]
        rule.setContent {
            SelectOutlinedTextField(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it },
                label = "Select Option"
            )
        }

        rule.onNodeWithTag(SELECT_OUTLINED_TEXT_FIELD_ARROW_DOWN_TAG, useUnmergedTree = true)
            .performClick()

        rule.onNodeWithText("Option 2", useUnmergedTree = true)
            .assertIsDisplayed()
            .performClick()

        assert(selectedOption == "Option 2")
    }

    @Test
    fun dropdown_menu_closes_when_option_is_selected() {
        val options = listOf("Option 1", "Option 2", "Option 3")
        var selectedOption = options[0]
        rule.setContent {
            SelectOutlinedTextField(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it },
                label = "Select Option"
            )
        }

        rule.onNodeWithTag(SELECT_OUTLINED_TEXT_FIELD_ARROW_DOWN_TAG, useUnmergedTree = true)
            .performClick()

        rule.onNodeWithText("Option 2", useUnmergedTree = true)
            .assertIsDisplayed()
            .performClick()

        rule.onNodeWithText("Option 2", useUnmergedTree = true)
            .assertExists()
        rule.onNodeWithText("Option 1")
            .assertDoesNotExist()
        rule.onNodeWithText("Option 3")
            .assertDoesNotExist()
    }

    @Test
    fun dropdown_menu_closes_when_dismissed() {
        val options = listOf("Option 1", "Option 2", "Option 3")
        var selectedOption = options[0]
        rule.setContent {
            SelectOutlinedTextField(
                options = options,
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it },
                label = "Select Option"
            )
        }

        rule.onNodeWithTag(SELECT_OUTLINED_TEXT_FIELD_LABEL_TAG, useUnmergedTree = true)
            .performClick()
            .performClick()

        rule.onNodeWithText("Option 1")
            .assertExists()
        rule.onNodeWithText("Option 2")
            .assertDoesNotExist()
        rule.onNodeWithText("Option 3")
            .assertDoesNotExist()
    }
}