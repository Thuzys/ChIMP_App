package com.example.chimp.about.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.about.model.Dev
import com.example.chimp.about.screen.composable.DEVELOPER_CONTENT_COMPLETE_BIO_TAG
import com.example.chimp.about.viewModel.state.AboutScreenState
import org.junit.Rule
import org.junit.Test

class ShowDialogAboutDevViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testShowDialogAboutDevView() {
        rule.setContent {
            ShowDialogAboutDevView(
                state = AboutScreenState.ShowDialog(dev = AboutScreenState.devs.first()),
            )
        }

        rule
            .onNodeWithTag(SHOW_DIALOG_ABOUT_DEV_VIEW_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun onShowingChangeCalled() {
        var onShowingChangeCalled = false
        val onChange: (Dev) -> Unit = { onShowingChangeCalled = true }
        rule.setContent {
            ShowDialogAboutDevView(
                state = AboutScreenState.ShowDialog(dev = AboutScreenState.devs.first()),
                onShowingChange = onChange
            )
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_COMPLETE_BIO_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(onShowingChangeCalled)
    }
}