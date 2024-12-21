package com.example.chimp.screens.channel.screen.view

import android.view.ViewTreeObserver
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import com.example.chimp.screens.channel.screen.composable.ChatHeader
import com.example.chimp.screens.channel.screen.composable.MakeMessage
import com.example.chimp.screens.channel.screen.composable.TextInput
import com.example.chimp.screens.ui.composable.LoadMoreIcon
import com.example.chimp.screens.ui.composable.ShowDialog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import java.sql.Timestamp

const val SCROLLING_VIEW = "ScrollingView"

private const val LAZY_COLUMN_FILL_MAX_HEIGHT = 0.9f

private const val LAZY_COLUMN_FILL_MAX_HEIGHT_WITH_KEYBOARD = 0.8f

private const val TEXT_INPUT_HEIGHT = 100

private const val TEXT_INPUT_HEIGHT_WITH_KEYBOARD = 200

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
    onInfoClick: () -> Unit = {},
    onDeleteOrLeave: () -> Unit = {},
    editChannel: () -> Unit = {},
    onSendMessage: (String) -> Unit = {},
    loadMore: () -> Unit = {}
) {
    var textInputHeight by remember { mutableStateOf(TEXT_INPUT_HEIGHT.dp) }
    var lazyColumnMaxHeight by remember { mutableFloatStateOf(LAZY_COLUMN_FILL_MAX_HEIGHT) }
    val view = LocalView.current
    val listState = rememberLazyListState()

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = android.graphics.Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.height
            val keypadHeight = screenHeight - rect.bottom
            textInputHeight = if (keypadHeight > screenHeight * 0.15) {
                TEXT_INPUT_HEIGHT_WITH_KEYBOARD.dp
            } else {
                TEXT_INPUT_HEIGHT.dp
            }
            lazyColumnMaxHeight = if (keypadHeight > screenHeight * 0.15) {
                LAZY_COLUMN_FILL_MAX_HEIGHT_WITH_KEYBOARD
            } else {
                LAZY_COLUMN_FILL_MAX_HEIGHT
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    Column(
        modifier = modifier
            .testTag(SCROLLING_VIEW)
            .windowInsetsPadding(WindowInsets.ime),
    ) {
        val messages by state.messages.collectAsState(emptyList())
        val hasMore by state.hasMore.collectAsState(false)
        var isToShowOptions by remember { mutableStateOf(false) }
        var firstElem by remember { mutableStateOf(messages.firstOrNull()) }
        LaunchedEffect(messages) {
            if (messages.firstOrNull() != firstElem) {
                firstElem = messages.first()
                listState.scrollToItem(0)
            }
        }
        ChatHeader(onBackClick, state.channel) { isToShowOptions = true }
        ShowDialog(
            showDialog = isToShowOptions,
            onDismissRequest = { isToShowOptions = false },
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimaryContainer),
                    onClick = onInfoClick
                ) {
                    Text(
                        text = "Channel Info",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                Button(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onErrorContainer),
                    onClick = onDeleteOrLeave
                ) {
                    Text(
                        text = if (state.user.id == state.channel.owner.id) "Delete Channel" else "Leave Channel",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (state.user.id == state.channel.owner.id) {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onPrimaryContainer),
                        onClick = editChannel
                    ) {
                        Text(
                            text = "Edit Channel",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxHeight(lazyColumnMaxHeight)
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
        if (state.accessControl == READ_WRITE) {
            TextInput(
                modifier = Modifier.height(textInputHeight),
                onSendMessage = onSendMessage
            )
        }
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
            .fillMaxSize(),
        state = Scrolling(
            channel = ChannelInfo(
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