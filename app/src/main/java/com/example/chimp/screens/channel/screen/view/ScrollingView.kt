package com.example.chimp.screens.channel.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import com.example.chimp.screens.channel.screen.composable.ChatHeader
import com.example.chimp.screens.channel.screen.composable.MakeMessage
import com.example.chimp.screens.channel.screen.composable.TextInput
import com.example.chimp.screens.ui.composable.LoadMoreIcon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import java.sql.Timestamp

const val SCROLLING_VIEW = "ScrollingView"

const val LAZY_COLUMN_FILL_MAX_HEIGHT = 0.9f

const val TEXT_INPUT_FILL_MAX_HEIGHT = 1f

/**
 * ScrollingView is a composable that displays the scrolling view of the channel.
 *
 * @param modifier the modifier for the scrolling view.
 * @param state the state of the scrolling view.
 * @param onBackClick the callback to be called when the back button is clicked.
 * @param onInfoClick the callback to be called when the info button is clicked.
 * @param onSendMessage the callback to be called when the send button is clicked.
 * @param loadMore the callback to be called when more messages need to be loaded.
 *
 */
@Composable
internal fun ScrollingView(
    modifier: Modifier = Modifier,
    state: Scrolling,
    onBackClick: () -> Unit = {},
    onInfoClick: (ChannelBasicInfo) -> Unit = {},
    onSendMessage: (String) -> Unit = {},
    loadMore: () -> Unit = {}
) {
    Column(
        modifier = modifier.testTag(SCROLLING_VIEW),
    ) {
        val messages by state.messages.collectAsState(emptyList())
        val hasMore by state.hasMore.collectAsState(false)
        var isToShowOptions by remember { mutableStateOf(false) }
        ChatHeader(onBackClick, state.channel) { isToShowOptions = true }
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(LAZY_COLUMN_FILL_MAX_HEIGHT)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(
                items = messages,
                key = { message ->
                    val key = checkNotNull(message.mId) { "Message ID cannot be null" }
                    key.toInt()
                }
            ) { message ->
                MakeMessage(message, state.user)
            }
            item(
                key = hasMore
            ) {
                if (hasMore) {
                    LoadMoreIcon(
                        modifier = Modifier.fillMaxWidth(),
                        onVisible = loadMore
                    )
                }
            }
        }
        TextInput(
            modifier = Modifier.fillMaxHeight(TEXT_INPUT_FILL_MAX_HEIGHT),
            onSendMessage = onSendMessage
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatPreview() {
    val user1 = UserInfo(1u, "User 1")
    val user2 = UserInfo(2u, "User 2")
    val list = listOf(
        Message(
            owner = user1,
            message = "Hello",
            time = Timestamp(System.currentTimeMillis()),
            mId = 1u,
            cId = 1u
        ),
        Message(
            owner = user2,
            message = "Hi",
            time = Timestamp(System.currentTimeMillis()),
            mId = 2u,
            cId = 1u
        ),
        Message(
            owner = user1,
            message = "How are you?",
            time = Timestamp(System.currentTimeMillis()),
            mId = 3u,
            cId = 1u
        ),
        Message(
            owner = user2,
            message = "I'm fine",
            time = Timestamp(System.currentTimeMillis()),
            mId = 4u,
            cId = 1u
        ),
        Message(
            owner = user1,
            message = "Good to hear with a really long message, " +
                    "that needs to be wrapped, in order" +
                    " to test the wrapping of the message",
            time = Timestamp(System.currentTimeMillis()),
            mId = 5u,
            cId = 1u
        ),
        Message(
            owner = user2,
            message = "Hi",
            time = Timestamp(System.currentTimeMillis()),
            mId = 6u,
            cId = 1u
        ),
    )
    val flow = MutableStateFlow(list)
    ScrollingView(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary)
            .fillMaxSize(),
        state = Scrolling(
            channel = ChannelBasicInfo(
                cId = 1u,
                name = ChannelName("Channel 1", "Channel 1"),
                owner = user1
            ),
            user = user1,
            messages = flow,
            hasMore = flowOf(false),
            accessControl = READ_WRITE
        )
    )
}