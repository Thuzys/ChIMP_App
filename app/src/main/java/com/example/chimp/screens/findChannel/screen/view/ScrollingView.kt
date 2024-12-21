package com.example.chimp.screens.findChannel.screen.view

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channels.screen.view.INFO_ICON_TAG
import com.example.chimp.screens.channels.screen.view.SWIPEABLE_ROW_TAG
import com.example.chimp.screens.findChannel.screen.FIND_CHANNEL_SCREEN_TAG
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.ui.composable.ActionIcon
import com.example.chimp.screens.ui.composable.ChatItemRow
import com.example.chimp.screens.ui.composable.LoadMoreIcon
import com.example.chimp.screens.ui.composable.ScrollHeader
import com.example.chimp.screens.ui.composable.SearchBar
import com.example.chimp.screens.ui.composable.SwipeableRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.flow.flowOf

/**
 * The tag for the header of the scrolling view.
 */
const val FIND_CHANNEL_SCROLLING_VIEW_HEADER_TAG = "FindChannelScrollingViewHeader"

/**
 * The tag for the join button.
 */
const val FIND_CHANNEL_BUTTON_TAG = "FindChannelButton"

/**
 * The tag for the swipe refresh.
 */
const val SWIPE_REFRESH_TAG = "SwipeRefresh"

/**
 * The height of the list item.
 */
private const val LIST_ITEM_HEIGHT = 90

/**
 * The padding for the list item.
 */
private const val LIST_ITEM_PADDING = 8

/**
 * The width of the action list.
 */
private const val ACTION_LIST_WIDTH = 90

/**
 * The width of the action icon.
 */
private const val ACTION_ICON_WIDTH = 45

/**
 * The padding for the action icon.
 */
private const val ACTION_ICON_PADDING = 8

@Composable
internal fun ScrollingView(
    modifier: Modifier,
    publicChannels: FindChannelScreenState.Scrolling,
    onLogout: () -> Unit = {},
    onInfoClick: (ChannelInfo) -> Unit = {},
    onReload: () -> Unit = {},
    onJoin: (UInt) -> Unit = {},
    onSearchChange: (String) -> Unit = {},
    onLoadMore: () -> Unit = {},
    onLoadMoreSearching: (String) -> Unit = {},
) {
    val channels by publicChannels.publicChannels.collectAsState(emptyList())
    val hasMore by publicChannels.hasMore.collectAsState(false)
    val searchChannelInput = publicChannels.searchChannelInput

    Column(
        modifier = modifier.testTag(FIND_CHANNEL_SCREEN_TAG),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScrollHeader(R.string.find_channels, onLogout)
        SearchBar(
            value = searchChannelInput,
            onValueChange = onSearchChange
        )
        SwipeRefresh(
            modifier = Modifier.testTag(SWIPE_REFRESH_TAG),
            state = SwipeRefreshState(false),
            onRefresh = {
                onReload()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = channels,
                    key = { index, channel -> "${channel.cId}_$index" }
                ) { _, channel ->
                    SwipeableRow(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .height(LIST_ITEM_HEIGHT.dp)
                            .testTag(SWIPEABLE_ROW_TAG)
                            .padding(top = LIST_ITEM_PADDING.dp),
                        actions = {
                            Row(
                                modifier = Modifier
                                    .width(ACTION_ICON_WIDTH.dp)
                                    .height(LIST_ITEM_HEIGHT.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ActionIcon(
                                    modifier = Modifier
                                        .width(ACTION_ICON_WIDTH.dp)
                                        .weight(1f)
                                        .testTag(INFO_ICON_TAG)
                                        .padding(end = ACTION_ICON_PADDING.dp)
                                        .height(LIST_ITEM_HEIGHT.dp),
                                    icon = Icons.Default.Info,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    onClick = { onInfoClick(channel) },
                                    contentDescription = "Channel info"
                                )
                            }
                        }
                    ) {
                        ChatItemRow(
                            modifier = Modifier
                                .testTag(FIND_CHANNEL_SCROLLING_VIEW_HEADER_TAG)
                                .height(LIST_ITEM_HEIGHT.dp)
                                .background(Color.Transparent),
                            chatItem = channel,
                            buttonModifier = Modifier.testTag(FIND_CHANNEL_BUTTON_TAG),
                            buttonString = stringResource(R.string.join_channel),
                            onClick = { onJoin(channel.cId) },
                        )
                    }
                }
                item(key = hasMore) {
                    if (hasMore) {
                        LoadMoreIcon(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(LIST_ITEM_HEIGHT.dp),
                            onVisible = {
                                if (publicChannels is FindChannelScreenState.NormalScrolling) {
                                    onLoadMore()
                                } else {
                                    onLoadMoreSearching(searchChannelInput)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollingViewPreview() {
    val publicChannels =
        FindChannelScreenState.NormalScrolling(
            flowOf(
                List(27) {
                    ChannelInfo(
                        cId = it.toUInt(),
                        name = ChannelName("Channel $it", "Channel $it"),
                        owner = UserInfo(it.toUInt(), "Owner $it")
                    )
                }
            ),
            flowOf(true)
        )
    ScrollingView(
        modifier = Modifier.fillMaxSize(),
        publicChannels = publicChannels
    )
}