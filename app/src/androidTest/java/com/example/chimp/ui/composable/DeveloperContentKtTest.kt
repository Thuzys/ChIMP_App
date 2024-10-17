package com.example.chimp.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.R
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.Email
import com.example.chimp.model.dev.SocialMedia
import org.junit.Rule
import org.junit.Test
import java.net.URL

class DeveloperContentKtTest {
    @get:Rule
    val rule = createComposeRule()

    private val dev = Dev(
        name = "Arthur Oliveira",
        email = Email("A50543@alunos.isel.pt"),
        socialMedia = SocialMedia(
            gitHub = URL("https://github.com/Thuzys"),
            linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
        ),
        number = "50543",
        imageId = R.drawable.thuzy_profile_pic
    )

    @Test
    fun developerContentShowsDeveloperContent() {
        rule.setContent { DeveloperContent(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_CONTAINER_TAG).assertIsDisplayed()
    }

    @Test
    fun developerContentShowsDeveloperImage() {
        rule.setContent { DeveloperContent(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_IMAGE_TAG, useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun developerContentShowsDeveloperName() {
        rule.setContent { DeveloperContent(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_NAME_TAG, useUnmergedTree = true).assertIsDisplayed()
    }

    @Test
    fun developerContentShowsDeveloperBio() {
        rule.setContent { DeveloperContent(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_BIO_TAG).assertIsDisplayed()
    }

    @Test
    fun developerContentShowsDeveloperSocialMedia() {
        rule.setContent { DeveloperContent(dev = dev) }
        rule
            .onAllNodesWithTag(DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG, useUnmergedTree = true)
            .onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun developerContentShowsDeveloperEmail() {
        rule.setContent { DeveloperContent(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_EMAIL_TAG).assertIsDisplayed()
    }

    @Test
    fun developerContentShowsDeveloperCompleteBio() {
        rule.setContent { DeveloperContent(dev = dev, showDialog = true) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_COMPLETE_BIO_TAG, useUnmergedTree = true)
            .assertIsDisplayed()
    }
}