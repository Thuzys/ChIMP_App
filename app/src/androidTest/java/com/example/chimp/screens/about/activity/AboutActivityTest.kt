package com.example.chimp.screens.about.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.screens.about.screen.view.IDLE_ABOUT_DEV_VIEW_TAG
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
}