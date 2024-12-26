package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test
import com.example.chimp.R
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status

class ScrollHeaderKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun scrollHeader_displaysLogoutIcon() {
        rule.setContent {
            ScrollHeader(titleResId = R.string.my_chats, logout = {})
        }

        rule.onNodeWithTag(LOGOUT_ICON_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun scrollHeader_triggersLogout_whenLogoutIconClicked() {
        var logoutTriggered = false
        rule.setContent {
            ScrollHeader(titleResId = R.string.my_chats, logout = { logoutTriggered = true })
        }

        rule.onNodeWithTag(LOGOUT_ICON_TAG)
            .performClick()

        assert(logoutTriggered)
    }

    @Test
    fun scrollHeader_displaysWarningIcon_whenDisconnected() {
        rule.setContent {
            ScrollHeader(titleResId = R.string.my_chats, logout = {}, connectivity = Status.DISCONNECTED)
        }

        rule.onNodeWithTag(SCROLL_HEADER_WARNING_ICON_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun scrollHeader_showsDialog_whenWarningIconClicked() {
        rule.setContent {
            ScrollHeader(titleResId = R.string.my_chats, logout = {}, connectivity = Status.DISCONNECTED)
        }

        rule.onNodeWithTag(SCROLL_HEADER_WARNING_ICON_TAG)
            .performClick()

        rule.onNodeWithText("No internet connection")
            .assertIsDisplayed()
    }
}