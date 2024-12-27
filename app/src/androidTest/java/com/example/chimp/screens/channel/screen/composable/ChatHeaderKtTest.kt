package com.example.chimp.screens.channel.screen.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import org.junit.Rule
import org.junit.Test

class ChatHeaderKtTest {
    @get:Rule
    val rule = createComposeRule()

    val channel = ChannelInfo(
        cId = 1u,
        name = ChannelName("test", "test"),
        owner = UserInfo(1u, "test")
    )

    @Test
    fun back_is_called_on_back_click() {
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ChatHeader(
                onBackClick = testFunc,
                channel = channel,
                onInfoClick = { }
            )
        }

        rule
            .onNodeWithTag(BUTTON_CHAT_HEADER_BACK_TEST_TAG)
            .performClick()

        assert(success) { "Back was not called" }
    }

    @Test
    fun info_is_called_on_info_click() {
        var success = false
        val testFunc = { _: ChannelInfo -> success = true }
        rule.setContent {
            ChatHeader(
                onBackClick = { },
                channel = channel,
                onInfoClick = testFunc
            )
        }

        rule
            .onNodeWithTag(BUTTON_CHAT_HEADER_INFO_TEST_TAG)
            .performClick()

        assert(success) { "Info was not called" }
    }
}