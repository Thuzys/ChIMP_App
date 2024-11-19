package com.example.chimp.screens.chats.screen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState.Idle
import com.example.chimp.screens.ui.composable.ActionIcon
import com.example.chimp.screens.ui.composable.ChatItemRow
import com.example.chimp.screens.ui.composable.SwipeableRow

const val CHATS_IDLE_VIEW_TAG = "ChatsIdleView"
const val CHATS_IDLE_VIEW_HEADER_TAG = "ChatsIdleViewHeader"

private const val LIST_ITEM_HEIGHT = 90

@Composable
internal fun IdleView(
    modifier: Modifier = Modifier,
    chats: Idle,
    onInfoClick: (ChannelBasicInfo) -> Unit = {},
    onChannelClick: (ChannelBasicInfo) -> Unit = {},
    onDeleteChannel: (ChannelBasicInfo) -> Unit = {}
) {
    Column(
        modifier = modifier.testTag(CHATS_IDLE_VIEW_TAG),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chats",
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = chats.channels,
                key = { _: Int, channel:ChannelBasicInfo -> channel.cId.toInt() }
            ) { _, channel ->
                SwipeableRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LIST_ITEM_HEIGHT.dp)
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    actions = {
                        Row(
                            modifier = Modifier
                                .fillParentMaxHeight()
                                .width(80.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                            ) {
                                ActionIcon(
                                    modifier = Modifier
                                        .fillParentMaxHeight()
                                        .weight(1f)
                                        .width(45.dp),
                                    icon = Icons.Default.Delete,
                                    backgroundColor = MaterialTheme.colorScheme.error,
                                    onClick = { onDeleteChannel(channel) },
                                    contentDescription = "Delete a channel"
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                            ) {
                                ActionIcon(
                                    modifier = Modifier
                                        .fillParentMaxHeight()
                                        .weight(1f)
                                        .width(45.dp),
                                    icon = Icons.Default.Info,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    onClick = { onInfoClick(channel) },
                                    contentDescription = "Channel info"
                                )
                            }
                        }
                    }
                ) {
                   ChatItemRow(
                        modifier = Modifier
                            .testTag(CHATS_IDLE_VIEW_HEADER_TAG)
                            .fillParentMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        chatItem = channel,
                        onClick = { onChannelClick(channel) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun IdleViewPreview() {
    val chats = Idle(List(27) {
        ChannelBasicInfo(
            cId = it.toUInt(),
            name = ChannelName("Channel $it")
        )
    })
    IdleView(
        modifier = Modifier.fillMaxSize(),
        chats = chats
    )
}