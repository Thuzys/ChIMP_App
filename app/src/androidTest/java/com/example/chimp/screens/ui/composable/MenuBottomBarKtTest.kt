package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import org.junit.Rule
import org.junit.Test

class MenuBottomBarKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun menuButton_isEnabled_whenChatsIsEnable() {
        rule.setContent {
            MenuBottomBar(chatsIsEnable = true)
        }

        rule.onNodeWithTag(MENU_HOME_BUTTON_TAG)
            .assertIsEnabled()
    }

    @Test
    fun menuButton_isDisabled_whenChatsIsNotEnable() {
        rule.setContent {
            MenuBottomBar(chatsIsEnable = false)
        }

        rule.onNodeWithTag(MENU_HOME_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun findChannelButton_isEnabled_whenFindChannelIsEnable() {
        rule.setContent {
            MenuBottomBar(findChannelIsEnable = true)
        }

        rule.onNodeWithTag(MENU_FIND_CHANNEL_BUTTON_TAG)
            .assertIsEnabled()
    }

    @Test
    fun findChannelButton_isDisabled_whenFindChannelIsNotEnable() {
        rule.setContent {
            MenuBottomBar(findChannelIsEnable = false)
        }

        rule.onNodeWithTag(MENU_FIND_CHANNEL_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun createChannelButton_isEnabled_whenCreateChannelIsEnable() {
        rule.setContent {
            MenuBottomBar(createChannelIsEnable = true)
        }

        rule.onNodeWithTag(MENU_CREATE_CHANNEL_BUTTON_TAG)
            .assertIsEnabled()
    }

    @Test
    fun createChannelButton_isDisabled_whenCreateChannelIsNotEnable() {
        rule.setContent {
            MenuBottomBar(createChannelIsEnable = false)
        }

        rule.onNodeWithTag(MENU_CREATE_CHANNEL_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun aboutButton_isEnabled_whenAboutIsEnable() {
        rule.setContent {
            MenuBottomBar(aboutIsEnable = true)
        }

        rule.onNodeWithTag(MENU_ABOUT_CHANNEL_BUTTON_TAG)
            .assertIsEnabled()
    }

    @Test
    fun aboutButton_isDisabled_whenAboutIsNotEnable() {
        rule.setContent {
            MenuBottomBar(aboutIsEnable = false)
        }

        rule.onNodeWithTag(MENU_ABOUT_CHANNEL_BUTTON_TAG)
            .assertIsNotEnabled()
    }

    @Test
    fun menuButton_triggersOnMenuClick_whenClicked() {
        var clicked = false
        rule.setContent {
            MenuBottomBar(onMenuClick = { clicked = true })
        }

        rule.onNodeWithTag(MENU_HOME_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }

    @Test
    fun findChannelButton_triggersFindChannelClick_whenClicked() {
        var clicked = false
        rule.setContent {
            MenuBottomBar(findChannelClick = { clicked = true })
        }

        rule.onNodeWithTag(MENU_FIND_CHANNEL_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }

    @Test
    fun createChannelButton_triggersCreateChannelClick_whenClicked() {
        var clicked = false
        rule.setContent {
            MenuBottomBar(createChannelClick = { clicked = true })
        }

        rule.onNodeWithTag(MENU_CREATE_CHANNEL_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }

    @Test
    fun aboutButton_triggersAboutClick_whenClicked() {
        var clicked = false
        rule.setContent {
            MenuBottomBar(aboutClick = { clicked = true })
        }

        rule.onNodeWithTag(MENU_ABOUT_CHANNEL_BUTTON_TAG)
            .performClick()

        assert(clicked)
    }
}