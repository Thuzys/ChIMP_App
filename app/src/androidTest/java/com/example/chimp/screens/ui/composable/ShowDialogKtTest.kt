package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ShowDialogKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showDialog_displaysContent_whenShowDialogIsTrue() {
        rule.setContent {
            ShowDialog(
                showDialog = true,
                onDismissRequest = {},
                content = {
                    Column {
                        Text("Dialog Content")
                    }
                }
            )
        }

        rule.onNodeWithText("Dialog Content")
            .assertIsDisplayed()
    }

    @Test
    fun showDialog_doesNotDisplayContent_whenShowDialogIsFalse() {
        rule.setContent {
            ShowDialog(
                showDialog = false,
                onDismissRequest = {},
                content = {
                    Column {
                        Text("Dialog Content")
                    }
                }
            )
        }

        rule.onNodeWithText("Dialog Content")
            .assertDoesNotExist()
    }
}