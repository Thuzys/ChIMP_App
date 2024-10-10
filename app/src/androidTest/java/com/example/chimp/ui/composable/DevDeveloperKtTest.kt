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
    private val dev = Dev(name, Email("mail@test.com"))

    @Test
    fun `show developer header`() {
        rule.setContent { AboutDeveloper(dev = dev) }
        rule.onNodeWithTag(HEADER_TAG).assertIsDisplayed()
    }

    @Test
    fun `the initial state of about developer do not have the contented displayed`() {
        rule.setContent { AboutDeveloper(dev = dev) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_TAG).isNotDisplayed()
    }

    @Test
    fun `show developer contend`() {
        rule.setContent { AboutDeveloper(dev = dev, isExpanded = true) }
        rule.onNodeWithTag(DEVELOPER_CONTENT_TAG).assertIsDisplayed()
    }

    @Test
    fun `onIsExpandedChange is called`() {
        var wasCalled = false
        rule.setContent { AboutDeveloper(dev = dev, onIsExpandedChange = { wasCalled = true }) }
        rule.onNodeWithTag(HEADER_TAG).performClick()
        assert(wasCalled)
    }

    @Test
    fun `onShowDialogChange is called`() {
        var wasCalled = false
        val bioTest = "bio test"
        rule.setContent {
            AboutDeveloper(dev = dev.copy(bio = bioTest), onShowDialogChange = { wasCalled = true }, isExpanded = true)
        }
        rule.onNodeWithText(bioTest).performClick()
        assert(wasCalled)
    }
}