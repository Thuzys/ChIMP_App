package com.example.chimp.about.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.about.screen.view.IDLE_ABOUT_DEV_VIEW_TAG
import org.junit.Rule
import org.junit.Test

class AboutActivityTest {
    @get:Rule
    val rule = createAndroidComposeRule<AboutActivity>()

    @Test
    fun initiallyTheIdleViewIsDisplayed() {
        rule
            .onNodeWithTag(IDLE_ABOUT_DEV_VIEW_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }
//
//    @Test
//    fun when_the_back_button_is_clicked_the_activity_is_finished() {
//        // Arrange
//        rule.waitUntil { rule.activityRule.scenario.state.isAtLeast(Lifecycle.State.STARTED) }
//        // Act
//        rule.activityRule.scenario.onActivity { it.onBackPressedDispatcher }
//        // Assert
//        rule.activityRule.scenario.onActivity { assert(it.isDestroyed) }
//    }
}