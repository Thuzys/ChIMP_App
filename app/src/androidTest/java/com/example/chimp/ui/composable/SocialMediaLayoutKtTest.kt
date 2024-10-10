package com.example.chimp.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.model.dev.SocialMedia
import org.junit.Rule
import org.junit.Test
import java.net.URL

class SocialMediaLayoutKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `test social media layout`() {
        var onClickFlag = false
        val gitOnClick = {
            onClickFlag = true
        }
        val socialMedia = SocialMedia(
            gitHub = URL("https://github.com/dummy"),
        )
        rule.setContent {
            SocialMediaLayout(
                social = socialMedia,
                gitOnClick = gitOnClick,
                linkedInOnClick = {}
            )
        }
        rule.onNodeWithTag(SOCIAL_MEDIA_LAYOUT_TAG, useUnmergedTree = true).performClick()
        assert(onClickFlag)
    }
}