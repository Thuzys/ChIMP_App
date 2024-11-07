package com.example.chimp.screens.about.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.about.model.Dev
import com.example.chimp.about.screen.composable.ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG
import org.junit.Rule
import org.junit.Test

class IdleAboutDevViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testIdleAboutDevView() {
        rule.setContent {
            IdleAboutDevView()
        }

        rule
            .onNodeWithTag(IDLE_ABOUT_DEV_VIEW_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun onIsExpandedChange() {
        var onIsExpandedChangeCalled = false
        val onChange: (Dev) -> Unit = { onIsExpandedChangeCalled = true }
        rule.setContent {
            IdleAboutDevView(onIsExpandedChange = onChange)
        }

        rule
            .onAllNodesWithTag(
                ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG,
                useUnmergedTree = true
            )
            .onFirst()
            .performClick()

        assert(onIsExpandedChangeCalled)
    }
}