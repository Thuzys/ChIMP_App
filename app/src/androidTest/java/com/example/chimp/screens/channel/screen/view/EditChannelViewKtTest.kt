package com.example.chimp.screens.channel.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Editing
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Loading
import org.junit.Rule
import org.junit.Test

class EditChannelViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    val channel = ChannelInfo(
        cId = 1u,
        name = ChannelName("Channel", "Channel"),
        owner = UserInfo(1u, "Owner")
    )

    @Test
    fun go_back_button_click() {
        val state = Editing(
            channel = channel,
            previous = Loading
        )
        var success = false
        val funcTest = { success = true }
        rule.setContent {
            EditChannelView(
                state = state,
                goBack = funcTest,
                onSave = {}
            )
        }

        rule
            .onNodeWithTag(EDIT_CHANNEL_GO_BACK_BUTTON_TAG)
            .performClick()

        assert(success)
    }

    @Test
    fun save_button_click() {
        val state = Editing(
            channel = channel,
            previous = Loading
        )
        var success = false
        val funcTest = { _:ChannelInfo -> success = true }
        rule.setContent {
            EditChannelView(
                state = state,
                goBack = {},
                onSave = funcTest
            )
        }

        rule
            .onNodeWithTag(EDIT_CHANNEL_SAVE_BUTTON_TAG)
            .performClick()

        assert(success)
    }
}