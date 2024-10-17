package com.example.chimp.ui.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.ui.view.about.SHOW_DIALOG_ABOUT_DEV_VIEW_TAG
import com.example.chimp.ui.view.about.ShowDialogAboutDevView
import com.example.chimp.viewModel.state.AboutScreenState
import org.junit.Rule
import org.junit.Test


class ShowDialogAboutDevViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testIsShowDialogAboutDevViewVisible() {
        rule.setContent {
            ShowDialogAboutDevView(
                state = AboutScreenState.ShowDialog(
                    dev = AboutScreenState.devs.first()
                )
            )
        }
        rule
            .onNodeWithTag(SHOW_DIALOG_ABOUT_DEV_VIEW_TAG)
            .assertIsDisplayed()
    }
}