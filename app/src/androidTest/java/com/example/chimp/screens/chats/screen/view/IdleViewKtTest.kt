package com.example.chimp.screens.chats.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.chats.screen.composable.CHATS_HEADER_IMAGE_TAG
import com.example.chimp.screens.chats.screen.composable.CHATS_HEADER_NAME_TAG
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState.Idle
import com.example.chimp.screens.ui.composable.ACTION_ICON_TAG
import org.junit.Rule
import org.junit.Test

class IdleViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun makeIdle(nr: Int = 1) =
        Idle(
            List(nr) {
                ChannelBasicInfo(
                    cId = nr.toUInt(),
                    name = ChannelName("${nr}ºChannel")
                )
            }
        )

    @Test
    fun test_onImageClick() {
        val idle = makeIdle()
        var result = false
        val testFunc: (ChannelBasicInfo) -> Unit = { result = true }
        rule.setContent {
            IdleView(
                chats = idle,
                onInfoClick = testFunc
            )
        }
        rule
            .onAllNodesWithTag(CHATS_HEADER_IMAGE_TAG)
            .onFirst()
            .performClick()
        assert(result)
    }

    @Test
    fun test_onChannelClick() {
        val idle = makeIdle()
        var result = false
        val testFunc: (ChannelBasicInfo) -> Unit = { result = true }
        rule.setContent {
            IdleView(
                chats = idle,
                onChannelClick = testFunc
            )
        }
        rule
            .onAllNodesWithTag(
                testTag = CHATS_HEADER_NAME_TAG,
                useUnmergedTree = true
            )
            .onFirst()
            .performClick()
        assert(result)
    }

    @Test
    fun test_onDeleteChannel() {
        val idle = makeIdle()
        var result = false
        val testFunc: (ChannelBasicInfo) -> Unit = { result = true }
        rule.setContent {
            IdleView(
                chats = idle,
                onDeleteChannel = testFunc
            )
        }

        rule
            .onAllNodesWithTag(
                testTag = CHATS_HEADER_NAME_TAG,
                useUnmergedTree = true
            )
            .onFirst()
            .performTouchInput {
                swipeRight()
            }

        rule
            .onAllNodesWithTag(
                testTag = ACTION_ICON_TAG,
                useUnmergedTree = true
            )
            .onFirst()
            .performClick()
        assert(result) { "onDeleteChannel not called" }
    }
}