package com.example.chimp.findChannel.screen.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.chats.model.channel.ChannelName
import com.example.chimp.findChannel.model.FindChannelItem
import com.example.chimp.findChannel.screen.composable.ChatItemRow
import com.example.chimp.findChannel.viewModel.FindChannelViewModel

@Composable
fun ChatList(
    modifier: Modifier = Modifier,
    vm: FindChannelViewModel
) {
    val chatItems by vm.chatItems.collectAsState()

    LazyColumn(modifier = modifier.fillMaxSize().scrollable(state = vm.scrollState, orientation = Orientation.Vertical)) {
        items(chatItems) { chatItem ->
            ChatItemRow(chatItem)
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListPreview() {
    val vm = FindChannelViewModel().apply {
        val list: MutableList<FindChannelItem> = mutableListOf()
        repeat (20) {
            list.add(
                FindChannelItem(
                    cId = it.toUInt(),
                    icon = R.drawable.github_mark,
                    name = ChannelName("Channel $it"),
                )
            )
        }
        updateChatItems(list)
    }
    ChatList(
        vm = vm
    )
}