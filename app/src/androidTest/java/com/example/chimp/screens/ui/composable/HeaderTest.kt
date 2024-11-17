package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.screens.ui.composable.Header
import com.example.chimp.screens.ui.composable.IMAGE_HEADER_TAG
import com.example.chimp.screens.ui.composable.TEXT_HEADER_TAG
import org.junit.Rule
import org.junit.Test

class HeaderTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun imageIsCorrectlyDisplayedOnHeaderTest() {
        rule.setContent { Header(profileName = "test") }
        rule.onNodeWithTag(IMAGE_HEADER_TAG).assertIsDisplayed()
    }

    @Test
    fun textIsCorrectlyDisplayedOnHeaderTest() {
        val profileName = "test"
        rule.setContent { Header(profileName = profileName) }
        rule.onNodeWithTag(TEXT_HEADER_TAG).assertIsDisplayed()
    }

    @Test
    fun textContendIsCorrectlyDisplayedOnHeaderTest() {
        val profileName = "test"
        rule.setContent { Header(profileName = profileName) }
        rule.onNodeWithTag(TEXT_HEADER_TAG).assertTextContains(profileName)
    }
}