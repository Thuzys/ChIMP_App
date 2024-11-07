package com.example.chimp.screens.about.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.screens.about.screen.composable.ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG
import com.example.chimp.screens.about.screen.composable.DEVELOPER_CONTENT_BIO_TAG
import com.example.chimp.screens.about.screen.composable.DEVELOPER_CONTENT_COMPLETE_BIO_TAG
import com.example.chimp.screens.about.screen.composable.DEVELOPER_CONTENT_CONTAINER_TAG
import com.example.chimp.screens.about.screen.view.IDLE_ABOUT_DEV_VIEW_TAG
import com.example.chimp.screens.about.screen.view.SHOWING_ABOUT_DEV_VIEW_TAG
import com.example.chimp.screens.about.screen.view.SHOW_DIALOG_ABOUT_DEV_VIEW_TAG
import com.example.chimp.screens.about.viewModel.AboutViewModel
import com.example.chimp.screens.about.viewModel.state.AboutScreenState
import org.junit.Rule
import org.junit.Test

class ChIMPAboutScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun idleViewIsShowingTest() {
        val viewModel = AboutViewModel()
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(IDLE_ABOUT_DEV_VIEW_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun showingViewIsShowingTest() {
        val viewModel = AboutViewModel()
        viewModel.showDev(AboutScreenState.devs.first())
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(SHOWING_ABOUT_DEV_VIEW_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun showDialogViewIsShowingTest() {
        val viewModel = AboutViewModel()
        viewModel.showDialog(AboutScreenState.devs.first())
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(SHOW_DIALOG_ABOUT_DEV_VIEW_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun changeStateToShowingTest() {
        val viewModel = AboutViewModel()
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }

        rule
            .onAllNodesWithTag(ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(viewModel.state is AboutScreenState.Showing)
    }

    @Test
    fun changeStateToIdleTest() {
        val viewModel = AboutViewModel()
        viewModel.showDev(AboutScreenState.devs.first())
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_CONTAINER_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(viewModel.state is AboutScreenState.Idle)
    }

    @Test
    fun changeStateToShowDialogTest() {
        val viewModel = AboutViewModel()
        viewModel.showDev(AboutScreenState.devs.first())
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_BIO_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(viewModel.state is AboutScreenState.ShowDialog)
    }

    @Test
    fun changeStateToShowingFromShowDialogTest() {
        val viewModel = AboutViewModel()
        viewModel.showDialog(AboutScreenState.devs.first())
        rule.setContent {
            ChIMPAboutScreen(viewModel = viewModel)
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_COMPLETE_BIO_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(viewModel.state is AboutScreenState.Showing)
    }
}