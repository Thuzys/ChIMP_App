package com.example.chimp.ui.composable

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.Email
import org.junit.Rule
import org.junit.Test

class DevDeveloperKtTest {
    @get:Rule
    val rule = createComposeRule()

    private val name = "Chimp"
    private val dev = Dev(name, number = "12345", Email("mail@test.com"))

    @Test
    fun showDeveloperHeader() {
        rule.setContent { AboutDeveloper(dev = dev) }
        rule.onNodeWithTag(HEADER_TAG).assertIsDisplayed()
    }

    @Test
    fun testInitState() {
        rule.setContent { AboutDeveloper(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_TAG).isNotDisplayed()
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
        rule.onNodeWithTag(HEADER_TAG).performClick()
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
}