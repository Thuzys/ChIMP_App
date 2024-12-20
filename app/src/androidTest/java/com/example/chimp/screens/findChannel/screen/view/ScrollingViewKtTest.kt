package com.example.chimp.screens.findChannel.screen.view

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channels.screen.view.CHANNEL_BUTTON_TAG
import com.example.chimp.screens.channels.screen.view.INFO_ICON_TAG
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.ui.composable.LOAD_MORE_ICON_TAG
import com.example.chimp.screens.ui.composable.LOGOUT_ICON_TAG
import com.example.chimp.screens.ui.composable.SEARCH_BAR_TAG
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ScrollingViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    private fun makeIdle(nr: Int = 1, hasMore: Boolean = false) =
        FindChannelScreenState.NormalScrolling(
            flowOf(
                List(nr) {
                    ChannelBasicInfo(
                        cId = nr.toUInt(),
                        name = ChannelName("${nr}ºChannel"),
                        owner = UserInfo(nr.toUInt(), "Owner"),
                    )
                }
            ),
            flowOf(hasMore)
        )

    @Test
    fun click_on_Join_button_calls_onJoin_function() {
        val idle = makeIdle()
        var success = false
        val testFunc: (UInt) -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
                onJoin = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = FIND_CHANNEL_BUTTON_TAG,
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
                modifier = Modifier,
                publicChannels = idle
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
                modifier = Modifier,
                publicChannels = idle
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
    fun load_more_is_called_when_LoadMoreIcon_is_displayed(){
        val idle = makeIdle(hasMore = true)
        var success = false
        val testFunc: () -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
                onLoadMore = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOAD_MORE_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()
        assert(success)
    }

    @Test
    fun load_more_is_not_called_when_LoadMoreIcon_is_not_displayed(){
        val idle = makeIdle(hasMore = false)
        var success = false
        val testFunc: () -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
                onLoadMore = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = LOAD_MORE_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()
        assert(!success)
    }

    @Test
    fun onInfoClick_is_called_when_info_button_is_clicked(){
        val idle = makeIdle()
        var success = false
        val testFunc: (ChannelBasicInfo) -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
                onInfoClick = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = INFO_ICON_TAG,
                useUnmergedTree = true
            )
            .performClick()
        assert(success)
    }

    @Test
    fun onLogoutClick_is_called_when_logout_button_is_clicked(){
        val idle = makeIdle()
        var success = false
        val testFunc: () -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
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

    @Test
    fun search_bar_is_displayed(){
        val idle = makeIdle()
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle
            )
        }

        rule
            .onNodeWithTag(
                testTag = SEARCH_BAR_TAG,
                useUnmergedTree = true
            )
            .assertExists()
    }

    @Test
    fun onSearchChange_is_called_when_search_bar_is_changed(){
        val idle = makeIdle()
        var success = false
        val testFunc: (String) -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
                onSearchChange = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = SEARCH_BAR_TAG,
                useUnmergedTree = true
            )
            .performClick()
            .performTextInput("test")
        assert(success)
    }

    @Test
    fun onReload_is_called_when_swipe_refresh_is_triggered() {
        val idle = makeIdle()
        var success = false
        val testFunc: () -> Unit = { success = true }
        rule.setContent {
            ScrollingView(
                modifier = Modifier,
                publicChannels = idle,
                onReload = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = SWIPE_REFRESH_TAG,
                useUnmergedTree = true
            )
            .performTouchInput { swipeDown() }
        assert(success)
    }
}