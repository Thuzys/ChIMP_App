package com.example.chimp.screens.about.screen.composable

import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.screens.about.model.Dev
import com.example.chimp.screens.about.model.Email
import com.example.chimp.screens.about.model.SocialMedia
import org.junit.Rule
import org.junit.Test
import java.net.URL

class DeveloperContentKtTest {
    @get:Rule
    val rule = createComposeRule()

    private val dev = Dev(
        "Chimp",
        number = "12345",
        Email("mail@test.com"),
        socialMedia = SocialMedia(
            gitHub = URL("https://github.com/test"),
            linkedIn = URL("https://linkedin.com/test"),
        )
    )
    @Test
    fun showDialogIsNotDisplayedTest() {
        rule.setContent {
            DeveloperContent(dev = dev)
        }

        rule
            .onNodeWithTag(DEVELOPER_CONTENT_COMPLETE_BIO_TAG)
            .assertIsNotDisplayed()
    }

    @Test
    fun gitOnClickTest() {
        var wasCalled = false
        rule.setContent {
            DeveloperContent(dev = dev, gitOnClick = { wasCalled = true })
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG)
            .onFirst()
            .performClick()
        assert(wasCalled)
    }

    @Test
    fun linkedInOnClickTest() {
        var wasCalled = false
        rule.setContent {
            DeveloperContent(dev = dev, linkedInOnClick = { wasCalled = true })
        }

        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG)
            .onLast()
            .performClick()
        assert(wasCalled)
    }

    @Test
    fun mailOnClickTest() {
        var wasCalled = false
        rule.setContent {
            DeveloperContent(dev = dev, emailOnClick = { wasCalled = true })
        }

        rule
            .onNodeWithTag(DEVELOPER_CONTENT_EMAIL_TAG)
            .performClick()

        assert(wasCalled)
    }

    @Test
    fun onShowDialogIsCalled() {
        var wasCalled = false
        rule.setContent {
            DeveloperContent(dev = dev, onShowDialog = { wasCalled = true })
        }

        rule
            .onNodeWithTag(DEVELOPER_CONTENT_BIO_TAG)
            .performClick()

        assert(wasCalled)
    }

    @Test
    fun onIsExpandedIsCalled() {
        var wasCalled = false
        rule.setContent {
            DeveloperContent(dev = dev, onIsExpanded = { wasCalled = true })
        }

        rule
            .onNodeWithTag(DEVELOPER_CONTENT_CONTAINER_TAG)
            .performClick()

        assert(wasCalled)
    }
}