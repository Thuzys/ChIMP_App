package com.example.chimp.screens.findChannel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.screens.ui.composable.ChatItemRow
import com.example.chimp.findChannel.screen.composable.SearchBar
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.findChannel.viewModel.state.FindChannel

@Composable
fun FindChannelView(
    modifier: Modifier = Modifier,
    vm: FindChannel,
    onJoin: (UInt) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SearchBar(
            modifier = modifier,
            value = vm.searchChannelInput,
            onValueChange = onValueChange,
            onSearch = { onSearch(vm.searchChannelInput) },
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            val channels = vm.publicChannels
            channels?.forEach { chatItem ->
                ChatItemRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    chatItem = chatItem,
                    buttonString = "Join",
                    onClick = { onJoin(chatItem.cId) }
                )
                Spacer(Modifier.width(5.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListPreview() {
    val channels = mutableListOf<ChannelBasicInfo>()
    repeat(50) {
        channels.add(
            ChannelBasicInfo(
                cId = it.toUInt(),
                name = ChannelName("Channel $it"),
                icon = R.drawable.github_mark,
                owner = UserInfo(it.toUInt(), "Owner $it")
            )
        )
    }
    val vm =
        FindChannel.FindChannelIdle(
            searchChannelInput = "",
            publicChannels = channels,

            )
    FindChannelView(
        vm = vm,
        onJoin = { }
    )
}