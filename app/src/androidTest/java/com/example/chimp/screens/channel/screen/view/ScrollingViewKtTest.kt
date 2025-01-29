package com.example.chimp.screens.channel.screen.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.CONNECTED
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.DISCONNECTED
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_ONLY
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import com.example.chimp.screens.channel.screen.composable.BUTTON_CHAT_HEADER_BACK_TEST_TAG
import com.example.chimp.screens.channel.screen.composable.BUTTON_CHAT_HEADER_INFO_TEST_TAG
import com.example.chimp.screens.channel.screen.composable.SEND_BUTTON_TAG
import com.example.chimp.screens.channel.screen.composable.TEXT_INPUT_TAG
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ScrollingViewKtTest {
    @get:Rule
    val rule = createComposeRule()

    val user = UserInfo(1u, "Name")

    val user2 = UserInfo(2u, "Name")

    val channel = ChannelInfo(
        cId = 1u,
        name = ChannelName("Name", "Name"),
        owner = user
    )

    @Test
    fun onBackClick_is_called_when_button_is_clicked() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                onBackClick = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_BACK_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun on_info_click_the_infoDialog_is_displayed() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        rule.setContent {
            ScrollingView(state = state)
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_INFO_DIALOG,
                useUnmergedTree = true
            )
            .assertIsDisplayed()
    }

    @Test
    fun onInfoClick_is_called_when_button_is_clicked() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                onInfoClick = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_INFO_BUTTON,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun onDeleteOrLeave_is_called_when_button_is_clicked() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                onDeleteOrLeave = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_DELETE_OR_LEAVE_BUTTON,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun onEditChannel_is_called_when_button_is_clicked_and_user_is_channel_owner() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                onEditChannel = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_EDIT_BUTTON,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun onCreateInvite_is_called_when_button_is_clicked_and_user_is_channel_owner() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                onCreateInvite = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_CREATE_INVITE_BUTTON,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun onEditChannel_is_not_displayed_when_user_is_not_channel_owner() {
        val state = Scrolling(
            channel = channel,
            user = user2,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        rule.setContent {
            ScrollingView(state = state)
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_EDIT_BUTTON,
                useUnmergedTree = true
            )
            .assertDoesNotExist()
    }

    @Test
    fun onCreateInvitation_is_not_displayed_when_user_is_not_channel_owner() {
        val state = Scrolling(
            channel = channel,
            user = user2,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        rule.setContent {
            ScrollingView(state = state)
        }

        rule
            .onNodeWithTag(
                testTag = BUTTON_CHAT_HEADER_INFO_TEST_TAG,
                useUnmergedTree = true
            )
            .performClick()

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_CREATE_INVITE_BUTTON,
                useUnmergedTree = true
            )
            .assertDoesNotExist()
    }

    @Test
    fun loadMore_is_called_when_loadIcon_is_visible() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(true),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                loadMore = testFunc
            )
        }

        assert(success)
    }


    @Test
    fun loadMore_is_not_called_when_hasMore_is_false() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = true
        val testFunc = { success = false }
        rule.setContent {
            ScrollingView(
                state = state,
                loadMore = testFunc
            )
        }

        assert(success)
    }

    @Test
    fun onSendMessage_is_called_when_send_icon_is_clicked() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        var success = false
        val testFunc = { _:String -> success = true }
        rule.setContent {
            ScrollingView(
                state = state,
                onSendMessage = testFunc
            )
        }

        rule
            .onNodeWithTag(
                testTag = SEND_BUTTON_TAG,
                useUnmergedTree = true
            )
            .performClick()

        assert(success)
    }

    @Test
    fun when_access_control_is_READ_ONLY_the_user_can_not_write() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_ONLY,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(CONNECTED)
        )
        rule.setContent {
            ScrollingView(state = state)
        }

        rule
            .onNodeWithTag(
                testTag = TEXT_INPUT_TAG,
                useUnmergedTree = true
            )
            .assertDoesNotExist()
    }

    @Test
    fun when_connection_is_DISCONNECTED_warning_is_visible() {
        val state = Scrolling(
            channel = channel,
            user = user,
            accessControl = READ_WRITE,
            messages = flowOf(emptyList()),
            hasMore = flowOf(false),
            connection = flowOf(DISCONNECTED),
        )
        rule.setContent {
            ScrollingView(state = state)
        }

        rule
            .onNodeWithTag(
                testTag = CHANNEL_SCROLLING_VIEW_NO_INTERNET_CONNECTION,
                useUnmergedTree = true
            )
            .assertIsDisplayed()
    }
}