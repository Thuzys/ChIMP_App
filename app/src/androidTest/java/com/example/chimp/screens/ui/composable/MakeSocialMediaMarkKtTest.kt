package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertContentDescriptionEquals
import org.junit.Rule
import org.junit.Test
import com.example.chimp.R

class MakeSocialMediaMarkKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun socialMediaMark_hasCorrectContentDescription() {
        rule.setContent {
            MakeSocialMediaMark(
                lightMode = R.drawable.github_mark,
                darkMode = R.drawable.github_mark_white,
                contentDescription = "GitHub Logo"
            )
        }

        rule.onNodeWithTag(SOCIAL_MEDIA_MARK_TAG)
            .assertContentDescriptionEquals("GitHub Logo")
    }
}