package com.example.chimp.screens.channels.screen.view

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.screens.channels.screen.composable.LOGOUT_ICON_TAG
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Scrolling
import com.example.chimp.screens.ui.composable.LOAD_MORE_ICON_TAG
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ScrollingViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun makeIdle(nr: Int = 1, hasMore: Boolean = false) =
        Scrolling(
            flowOf(
                List(nr) {
                    ChannelBasicInfo(
                        cId = nr.toUInt(),
                        name = ChannelName("${nr}ºChannel")
                    )
                }
            ),
            flowOf(hasMore)
        )

    @Test
    fun click_on_enter_button() {
        val idle = makeIdle()
        var success = false
        val testFunc: (ChannelBasicInfo) -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                chats = idle,
                onChannelClick = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = CHANNEL_BUTTON_TAG,
                useUnmergedTree = true
            )
            .performClick()
        assert(success)
    }

    @Test
    fun loadMore_is_displayed_when_hasMore() {
        val idle = makeIdle(hasMore = true)
        rule.setContent {
            ScrollingView(
                chats = idle
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOAD_MORE_ICON_TAG,
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun loadMore_is_not_displayed_when_hasMore_is_false() {
        val idle = makeIdle(hasMore = false)
        rule.setContent {
            ScrollingView(
                chats = idle
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOAD_MORE_ICON_TAG,
                useUnmergedTree = true
            )
            .assertDoesNotExist()
    }

    @Test
    fun loadMore_is_called_when_LoadMoreIcon_is_displayed() {
        val idle = makeIdle(hasMore = true)
        var success = false
        val testFunc: () -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                chats = idle,
                onLoadMore = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOAD_MORE_ICON_TAG,
                useUnmergedTree = true
            )
            .assertExists()

        assert(success)
    }

    @Test
    fun loadMore_is_not_called_when_LoadMoreIcon_is_not_displayed() {
        val idle = makeIdle(hasMore = false)
        var success = true
        val testFunc: () -> Unit = { success = false }
        rule.setContent {
            ScrollingView(
                chats = idle,
                onLoadMore = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOAD_MORE_ICON_TAG,
                useUnmergedTree = true
            )
            .assertDoesNotExist()

        assert(success)
    }

    @Test
    fun onInfoClick_is_called_when_info_icon_is_clicked() {
        val idle = makeIdle()
        var success = false
        val testFunc: (ChannelBasicInfo) -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                chats = idle,
                onInfoClick = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = SWIPEBLE_ROW_TAG,
                useUnmergedTree = true
            )
            .performTouchInput { swipeRight() }

        rule
            .onNodeWithTag(
                testTag = INFO_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun onDeleteOrLeave_is_called_when_deleteOrLeave_icon_is_clicked() {
        val idle = makeIdle()
        var success = false
        val testFunc: (ChannelBasicInfo) -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                chats = idle,
                onDeleteOrLeave = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = SWIPEBLE_ROW_TAG,
                useUnmergedTree = true
            )
            .performTouchInput { swipeRight() }

        rule
            .onNodeWithTag(
                testTag = DELETE_OR_LEAVE_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun onLogoutClick_is_called_when_logout_icon_is_clicked() {
        val idle = makeIdle()
        var success = false
        val testFunc: () -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                chats = idle,
                onLogout = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOGOUT_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }
}