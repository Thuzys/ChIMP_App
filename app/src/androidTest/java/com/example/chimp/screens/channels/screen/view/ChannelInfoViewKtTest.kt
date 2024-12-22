package com.example.chimp.screens.channels.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.screens.ui.views.CHANNEL_DESCRIPTION_TAG
import com.example.chimp.screens.ui.views.CHANNEL_INFO_BACK_BUTTON
import com.example.chimp.screens.ui.views.CHANNEL_INFO_SMALL_DESCRIPTION_TAG
import com.example.chimp.screens.ui.views.ChannelInfoView
import org.junit.Rule
import org.junit.Test

class ChannelInfoViewKtTest {

    @get:Rule
    val rule = createComposeRule()

    private val name = "Channel Name"
    private val displayName = "Channel Display Name"
    private val channelInfo =
        ChannelInfo(
            cId = 1u,
            name = ChannelName(displayName, name),
            description = "Channel Description",
            owner = UserInfo(1u, "test"),
        )

    @Test
    fun channel_name_is_displayed() {
        rule.setContent {
            ChannelInfoView(channel = channelInfo)
        }

        rule
            .onNodeWithText(name)
            .assertExists()
    }

    @Test
    fun channel_description_is_displayed() {
        rule.setContent {
            ChannelInfoView(channel = channelInfo)
        }

        rule
            .onNodeWithTag(CHANNEL_INFO_SMALL_DESCRIPTION_TAG)
            .assertExists()
    }

    @Test
    fun when_description_is_clicked_show_description() {
        rule.setContent {
            ChannelInfoView(channel = channelInfo)
        }

        rule
            .onNodeWithTag(CHANNEL_INFO_SMALL_DESCRIPTION_TAG)
            .performClick()

        rule
            .onNodeWithTag(CHANNEL_DESCRIPTION_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun onGoBackClick_is_called_when_back_button_is_clicked() {
        var success = false
        val funcTest = { success = true }
        rule.setContent {
            ChannelInfoView(
                channel = channelInfo,
                onGoBackClick = funcTest
            )
        }

        rule
            .onNodeWithTag(CHANNEL_INFO_BACK_BUTTON)
            .assertIsEnabled()
            .performClick()

        assert(success)
    }
}