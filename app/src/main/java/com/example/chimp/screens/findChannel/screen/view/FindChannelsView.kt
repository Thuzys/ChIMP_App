package com.example.chimp.screens.findChannel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.screens.ui.composable.ChatItemRow
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.ui.composable.SearchBar
import kotlinx.coroutines.flow.flowOf
import com.example.chimp.models.users.UserInfo

@Composable
fun IdleView(
    modifier: Modifier = Modifier,
    state: FindChannelScreenState.Idle,
    onJoin: (UInt) -> Unit,
    onSearch: (String) -> Unit = {},
    onFetchMore: () -> Unit = {}
) {
    val (searchBarInput, setSearchBarInput) = rememberSaveable { mutableStateOf("") }
    val channels by state.publicChannels.collectAsState(emptyList())
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = channels,
                key = { _: Int, channel: ChannelBasicInfo -> channel.cId }
            ) { index, channel ->
                ChatItemRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    chatItem = channel,
                    buttonString = "Join",
                    onClick = { onJoin(channel.cId) }
                )
                Spacer(Modifier.width(5.dp))

                if (index == channels.lastIndex) {
                    onFetchMore()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListPreview() {
    val state = FindChannelScreenState.Idle(
        publicChannels = flowOf(List(37) {
            ChannelBasicInfo(
                cId = it.toUInt(),
                name = ChannelName("Channel $it"),
                icon = R.drawable.github_mark,
                owner = UserInfo(it.toUInt(), "Owner $it")
            )
        })
    )
    IdleView(
        state = state,
        onJoin = { _ -> }
    )
}