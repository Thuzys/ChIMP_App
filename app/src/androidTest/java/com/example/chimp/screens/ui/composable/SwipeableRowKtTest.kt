package com.example.chimp.screens.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.performTouchInput
import org.junit.Rule
import org.junit.Test

class SwipeableRowKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun swipeableRow_showsActions_whenSwipedRight() {
        rule.setContent {
            SwipeableRow(
                modifier = Modifier.testTag("SwipeableRow"),
                actions = {
                    ActionIcon(
                        modifier = Modifier.testTag("ActionIcon"),
                        backgroundColor = Color.Red,
                        icon = Icons.Default.Delete,
                    )
                }
            ) {
                Text("Content")
            }
        }

        rule.onNodeWithTag("SwipeableRow")
            .performTouchInput { swipeRight() }

        rule.onNodeWithTag("ActionIcon")
            .assertIsDisplayed()
    }

    @Test
    fun swipeableRow_hidesActions_whenSwipedLeft() {
        rule.setContent {
            SwipeableRow(
                modifier = Modifier.testTag("SwipeableRow"),
                actions = {
                    Text("Action")
                }
            ) {
                Text("Content")
            }
        }

        rule.onNodeWithTag("SwipeableRow")
            .performTouchInput { swipeRight() }

        rule.onNodeWithTag("SwipeableRow")
            .performTouchInput { swipeLeft() }

        rule.onNodeWithTag("ActionIcon")
            .assertIsNotDisplayed()
    }

    @Test
    fun swipeableRow_staysOpen_whenSwipedHalfway() {
        rule.setContent {
            SwipeableRow(
                modifier = Modifier.testTag("SwipeableRow"),
                actions = {
                    ActionIcon(
                        modifier = Modifier.testTag("ActionIcon"),
                        backgroundColor = Color.Red,
                        icon = Icons.Default.Delete,
                    )
                }
            ) {
                Text("Content")
            }
        }

        rule.onNodeWithTag("SwipeableRow")
            .performTouchInput { swipeRight(startX = 0.5f, endX = 0.75f) }

        rule.onNodeWithTag("ActionIcon")
            .assertIsDisplayed()
    }
}
