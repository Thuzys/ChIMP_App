package com.example.chimp.screens.about.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.screens.about.model.Dev
import com.example.chimp.screens.about.screen.composable.ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG
import com.example.chimp.screens.about.screen.composable.DEVELOPER_CONTENT_BIO_TAG
import com.example.chimp.screens.about.screen.composable.DEVELOPER_CONTENT_CONTAINER_TAG
import com.example.chimp.screens.about.viewModel.state.AboutScreenState
import org.junit.Rule
import org.junit.Test

class ShowingAboutDevViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testShowingAboutDevView() {
        rule.setContent {
            ShowingAboutDevView(
                state = AboutScreenState.Showing(dev = AboutScreenState.devs.first())
            )
        }

        rule
            .onNodeWithTag(SHOWING_ABOUT_DEV_VIEW_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun onIdleChange() {
        var onIdleChangeCalled = false
        val onIdleChange: () -> Unit = { onIdleChangeCalled = true }
        rule.setContent {
            ShowingAboutDevView(
                state = AboutScreenState.Showing(dev = AboutScreenState.devs.first()),
                onIdleChange = onIdleChange
            )
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_CONTAINER_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(onIdleChangeCalled)
    }

    @Test
    fun onShowDialogChange() {
        var onShowDialogChangeCalled = false
        val onShowDialogChange: (Dev) -> Unit = { onShowDialogChangeCalled = true }
        rule.setContent {
            ShowingAboutDevView(
                state = AboutScreenState.Showing(dev = AboutScreenState.devs.first()),
                onShowDialogChange = onShowDialogChange
            )
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_BIO_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(onShowDialogChangeCalled)
    }

    @Test
    fun onIsExpandedChange() {
        var onIsExpandedChangeCalled = false
        val onIsExpandedChange: (Dev) -> Unit = { onIsExpandedChangeCalled = true }
        rule.setContent {
            ShowingAboutDevView(
                state = AboutScreenState.Showing(dev = AboutScreenState.devs.first()),
                onIsExpandedChange = onIsExpandedChange
            )
        }

        rule
            .onAllNodesWithTag(ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(onIsExpandedChangeCalled)
    }
}