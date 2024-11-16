package com.example.chimp.screens.findChannel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelItem
import com.example.chimp.screens.findChannel.screen.composable.ChatItemRow
import com.example.chimp.screens.ui.composable.SearchBar
import com.example.chimp.screens.findChannel.viewModel.state.FindChannel
import kotlin.reflect.KFunction2

@Composable
fun FindChannelView(
    modifier: Modifier = Modifier,
    vm: FindChannel,
    onJoin: (UInt, String?) -> Unit,
    onSearch: (String) -> Unit = {}
) {
    val (searchBarInput, setSearchBarInput) = rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SearchBar(
            modifier = modifier,
            value = searchBarInput,
            onValueChange = { setSearchBarInput(it) },
            onSearch = { onSearch(searchBarInput) },
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            val channels = vm.publicChannels
            channels?.forEach { chatItem ->
                ChatItemRow(
                    chatItem = chatItem,
                    onJoin = { onJoin(chatItem.cId, null) }
                )
                Spacer(Modifier.width(5.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListPreview() {
    val channels = mutableListOf<FindChannelItem>()
    repeat(50) {
        channels.add(
            FindChannelItem(
                cId = it.toUInt(),
                name = ChannelName("Channel $it"),
                icon = R.drawable.github_mark,
            )
        )
    }
    val vm =
        FindChannel.FindChannelIdle(
            publicChannels = channels,

            )
    FindChannelView(
        vm = vm,
        onJoin = { _, _ -> }
    )
}