package com.example.chimp.screens.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import org.junit.Rule
import org.junit.Test

class MyTextFieldKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun myTextField_displaysLabel() {
        rule.setContent {
            MyTextField(label = "Label")
        }

        rule.onNodeWithText("Label")
            .assertIsDisplayed()
    }

    @Test
    fun myTextField_displaysPlaceholder_whenEmpty() {
        rule.setContent {
            MyTextField(label = "Label", value = "")
        }

        rule.onNodeWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .assertTextEquals("")
    }

    @Test
    fun myTextField_updatesText_whenInputChanged() {
        var text = ""
        rule.setContent {
            MyTextField(label = "Label", value = text, onValueChange = { text = it })
        }

        rule.onNodeWithTag(MY_TEXT_FIELD_INPUT_TAG)
            .performTextInput("New input")

        assert(text == "New input")
    }

    @Test
    fun myTextField_triggersOnTrailingIconChange_whenIconClicked() {
        var iconClicked = false
        rule.setContent {
            MyTextField(
                label = "Label",
                value = "Text",
                trailingIcon = Icons.Default.Clear,
                onTrailingIconChange = { iconClicked = true }
            )
        }

        rule.onNodeWithTag(MY_TEXT_FIELD_TRAILING_ICON_TAG)
            .performClick()

        assert(iconClicked)
    }

    @Test
    fun myTextField_doesNotDisplayTrailingIcon_whenIconIsNull() {
        rule.setContent {
            MyTextField(label = "Label", value = "Text")
        }

        rule.onNodeWithTag(MY_TEXT_FIELD_TRAILING_ICON_TAG)
            .assertDoesNotExist()
    }
}