package com.example.chimp.screens.chats.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.models.users.User
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.screens.chats.model.messages.Messages
import com.example.chimp.screens.chats.screen.composable.ChatHeader
import com.example.chimp.screens.chats.screen.composable.MakeMessage
import com.example.chimp.screens.chats.screen.composable.TextInput
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import java.sql.Timestamp

@Composable
fun ChatView(
    modifier: Modifier = Modifier,
    state: ChatsScreenState.ChatSelected,
    onBackClick: () -> Unit = {},
    onInfoClick: (ChannelBasicInfo) -> Unit = {},
    onSendMessage: (String) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        val messages by state.messages.collectAsState(emptyList())
        ChatHeader(onBackClick, state.channel, onInfoClick)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            items(
                items = messages,
                key = { message -> message.mId.toInt() }
            ) { message ->
                MakeMessage(message, state.user)
            }
        }
        TextInput(onSendMessage)
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatPreview() {
    val flow = MutableStateFlow(
        listOf(
            Messages(
                owner = User(1u, "User 1", "user1"),
                message = "Hello",
                time = Timestamp(System.currentTimeMillis()),
                mId = 1u,
                cId = 1u
            ),
            Messages(
                owner = User(2u, "User 2", "user2"),
                message = "Hi",
                time = Timestamp(System.currentTimeMillis()),
                mId = 2u,
                cId = 1u
            ),
            Messages(
                owner = User(1u, "User 1", "user1"),
                message = "How are you?",
                time = Timestamp(System.currentTimeMillis()),
                mId = 3u,
                cId = 1u
            ),
            Messages(
                owner = User(2u, "User 2", "user2"),
                message = "I'm fine",
                time = Timestamp(System.currentTimeMillis()),
                mId = 4u,
                cId = 1u
            ),
            Messages(
                owner = User(1u, "User 1", "user1"),
                message = "Good to hear with a really long message, " +
                        "that needs to be wrapped, in order" +
                        " to test the wrapping of the message",
                time = Timestamp(System.currentTimeMillis()),
                mId = 5u,
                cId = 1u
            ),
        )
    )
    ChatView(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSecondary)
            .fillMaxSize(),
        state = ChatsScreenState.ChatSelected(
            user = User(1u, "User 1", "user1"),
            channel = ChannelBasicInfo(
                cId = 1u,
                name = ChannelName("Channel 1")
            ),
            _messages = flow,
        ),
    )
}