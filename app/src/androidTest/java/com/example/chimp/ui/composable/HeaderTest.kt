package com.example.chimp.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class HeaderTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `image is correctly displayed on header test`() {
        rule.setContent { Header(profileName = "test") }
        rule.onNodeWithTag(IMAGE_HEADER_TAG).assertIsDisplayed()
    }

    @Test
    fun `text is correctly displayed on header test`() {
        val profileName = "test"
        rule.setContent { Header(profileName = profileName) }
        rule.onNodeWithTag(TEXT_HEADER_TAG).assertIsDisplayed()
    }

    @Test
    fun `text contend is correctly displayed on header test`() {
        val profileName = "test"
        rule.setContent { Header(profileName = profileName) }
        rule.onNodeWithTag(TEXT_HEADER_TAG).assertTextContains(profileName)
    }
}