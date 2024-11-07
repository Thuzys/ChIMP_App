package com.example.chimp.screens.about.screen.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chimp.screens.about.model.Dev
import com.example.chimp.screens.about.model.Email
import com.example.chimp.screens.about.model.SocialMedia
import org.junit.Rule
import org.junit.Test
import java.net.URL

class AboutDeveloperKtTest {
    @get:Rule
    val rule = createComposeRule()

    private val name = "Chimp"
    private val dev = Dev(
        name,
        number = "12345",
        Email("mail@test.com"),
        socialMedia = SocialMedia(
            gitHub = URL("https://github.com/test"),
            linkedIn = URL("https://linkedin.com/test"),
        )
    )

    @Test
    fun showDeveloperHeader() {
        rule.setContent { AboutDeveloper(dev = dev) }
        rule
            .onNodeWithTag(ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun testInitState() {
        rule.setContent { AboutDeveloper(dev = dev) }
        rule
            .onNodeWithTag(DEVELOPER_CONTENT_TAG)
            .isNotDisplayed()
    }

    @Test
    fun showDeveloperContent() {
        rule.setContent { AboutDeveloper(dev = dev, isExpanded = true) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_TAG).assertIsDisplayed()
    }

    @Test
    fun onIsExpandedIsCalled() {
        var wasCalled = false
        rule.setContent { AboutDeveloper(dev = dev, onIsExpandedChange = { wasCalled = true }) }
        rule.onNodeWithTag(ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG).performClick()
        assert(wasCalled)
    }

    @Test
    fun onShowDialogChangeIsCalled() {
        var wasCalled = false
        val bioTest = "bio test"
        rule.setContent {
            AboutDeveloper(dev = dev.copy(bio = bioTest), onShowDialogChange = { wasCalled = true }, isExpanded = true)
        }
        rule.onNodeWithText(bioTest).performClick()
        assert(wasCalled)
    }

    @Test
    fun gitOnClickIsCalled() {
        var wasCalled = false
        rule.setContent {
            AboutDeveloper(
                dev = dev,
                gitOnClick = { wasCalled = true },
                isExpanded = true
            )
        }
        rule.onAllNodesWithTag(DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG, useUnmergedTree = true)
            .onFirst()
            .performClick()

        assert(wasCalled)
    }

    @Test
    fun linkedInOnClickIsCalled() {
        var wasCalled = false
        rule.setContent {
            AboutDeveloper(
                dev = dev,
                linkedInOnClick = { wasCalled = true },
                isExpanded = true
            )
        }
        rule.onAllNodesWithTag(DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG, useUnmergedTree = true)
            .onLast()
            .performClick()

        assert(wasCalled)
    }

    @Test
    fun emailOnClickIsCalled() {
        var wasCalled = false
        rule.setContent {
            AboutDeveloper(
                dev = dev,
                emailOnClick = { wasCalled = true },
                isExpanded = true
            )
        }
        rule
            .onNodeWithTag(DEVELOPER_CONTENT_EMAIL_TAG, useUnmergedTree = true)
            .performClick()

        assert(wasCalled)
    }
}