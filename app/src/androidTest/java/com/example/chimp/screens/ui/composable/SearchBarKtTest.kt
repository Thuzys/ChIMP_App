package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import org.junit.Rule
import org.junit.Test

class SearchBarKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun searchBar_displaysPlaceholderText_whenEmpty() {
        rule.setContent {
            SearchBar(value = "")
        }

        rule.onNodeWithText("Search...")
            .assertIsDisplayed()
    }

    @Test
    fun searchBar_updatesText_whenInputChanged() {
        var text = ""
        rule.setContent {
            SearchBar(value = text, onValueChange = { text = it })
        }

        rule.onNodeWithTag(SEARCH_BAR_TAG)
            .performTextInput("New input")

        assert(text == "New input")
    }

    @Test
    fun searchBar_triggersOnSearch_whenImeActionSearch() {
        var searchTriggered = false
        rule.setContent {
            SearchBar(value = "Search query", onSearch = { searchTriggered = true })
        }

        rule.onNodeWithTag(SEARCH_BAR_TAG)
            .performImeAction()

        assert(searchTriggered)
    }
}