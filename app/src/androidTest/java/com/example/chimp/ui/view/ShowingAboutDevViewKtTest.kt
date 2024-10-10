package com.example.chimp.ui.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.viewModel.AboutScreenState
import org.junit.Rule
import org.junit.Test


class ShowingAboutDevViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testShowingAboutDevViewIsVisible() {
        rule.setContent {
            ShowingAboutDevView(
                state = AboutScreenState.Showing(
                    dev = AboutScreenState.devs.first()
                )
            )
        }
        rule
            .onNodeWithTag(SHOWING_ABOUT_DEV_VIEW_TAG)
            .assertIsDisplayed()
    }
}
