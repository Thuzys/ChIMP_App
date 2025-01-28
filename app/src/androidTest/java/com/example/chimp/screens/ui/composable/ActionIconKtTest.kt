package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.performClick
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import org.junit.Rule
import org.junit.Test

class ActionIconKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun actionIcon_isDisplayed() {
        rule.setContent {
            ActionIcon(
                backgroundColor = Color.Red,
                icon = Icons.Default.Delete,
                contentDescription = "Delete Icon"
            )
        }

        rule.onNodeWithTag(ACTION_ICON_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun actionIcon_hasCorrectContentDescription() {
        rule.setContent {
            ActionIcon(
                backgroundColor = Color.Red,
                icon = Icons.Default.Delete,
                contentDescription = "Delete Icon"
            )
        }

        rule.onNodeWithTag(ACTION_ICON_TAG)
            .assertContentDescriptionEquals("Delete Icon")
    }

    @Test
    fun actionIcon_triggersOnClick() {
        var clicked = false
        rule.setContent {
            ActionIcon(
                backgroundColor = Color.Red,
                icon = Icons.Default.Delete,
                onClick = { clicked = true }
            )
        }

        rule.onNodeWithTag(ACTION_ICON_TAG)
            .performClick()

        assert(clicked)
    }
}