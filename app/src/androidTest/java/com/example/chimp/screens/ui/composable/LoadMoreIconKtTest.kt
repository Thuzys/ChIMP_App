package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import org.junit.Rule
import org.junit.Test

class LoadMoreIconKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun loadMoreIcon_isDisplayed() {
        rule.setContent {
            LoadMoreIcon()
        }

        rule.onNodeWithTag(LOAD_MORE_ICON_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun onVisible_isCalled_whenIconIsVisible() {
        var isVisible = false
        rule.setContent {
            LoadMoreIcon(onVisible = { isVisible = true })
        }

        rule.onNodeWithTag(LOAD_MORE_ICON_TAG)
            .performClick()

        assert(isVisible)
    }
}