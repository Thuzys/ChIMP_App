package com.example.chimp.screens.ui.composable

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo

class ChatItemRowKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun chatItemRow_buttonClickTriggersOnClick() {
        var clicked = false
        val item = ChannelInfo(
            1u,
            ChannelName("Channel", "Channel"),
            owner = UserInfo(1u, "User"),
        )
        rule.setContent {
            ChatItemRow(chatItem = item, buttonString = "Join", onClick = { clicked = true })
        }

        rule.onNodeWithText("Join")
            .performClick()

        assert(clicked)
    }

    @Test
    fun channelItemRow_shows_ChannelInfo() {
        val item = ChannelInfo(
            1u,
            ChannelName("Channel", "Channel"),
            owner = UserInfo(1u, "User"),
        )
        rule.setContent {
            ChatItemRow(chatItem = item, buttonString = "Join", onClick = { })
        }

        rule.onNodeWithText("Channel")
            .assertIsDisplayed()
    }
}