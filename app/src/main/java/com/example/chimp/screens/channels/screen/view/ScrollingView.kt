package com.example.chimp.screens.channels.screen.view

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
import androidx.compose.material.icons.filled.Delete
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
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.ui.composable.ScrollHeader
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Scrolling
import com.example.chimp.screens.ui.composable.ActionIcon
import com.example.chimp.screens.ui.composable.ChatItemRow
import com.example.chimp.screens.ui.composable.LoadMoreIcon
import com.example.chimp.screens.ui.composable.SwipeableRow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.flow.flowOf


/**
 * The tag for the ChannelScrollView.
 */
const val CHANNEL_SCROLLING_VIEW = "ChatsIdleView"


/**
 * The tag for the header of the IdleView.
 */
const val CHATS_IDLE_VIEW_HEADER_TAG = "ChatsIdleViewHeader"

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

/**
 * The tag for the channel button.
 */
const val CHANNEL_BUTTON_TAG = "ChannelButton"

/**
 * The tag for the swipeable row.
 */
const val SWIPEABLE_ROW_TAG = "SwipeableRow"

/**
 * The tag for the info icon.
 */
const val INFO_ICON_TAG = "InfoIcon"

/**
 * The tag for the delete or leave icon.
 */
const val DELETE_OR_LEAVE_ICON_TAG = "DeleteOrLeaveIcon"

/**
 * ScrollView is the view that displays the list of channels.
 *
 * This view is responsible for displaying the list of channels and handling user interactions.
 *
 * @param modifier Modifier The modifier to be applied to the view.
 * @param chats Scrolling The scrolling state of the channels.
 * @param onInfoClick Function(ChannelBasicInfo) The function to be called when the info icon is clicked.
 * @param onLogout Function() The function to be called when the logout icon is clicked.
 * @param onChannelClick Function(ChannelBasicInfo) The function to be called when a channel is clicked.
 * @param onDeleteOrLeave Function(ChannelBasicInfo) The function to be called when the delete icon is clicked.
 * @param onReload Function() The function to be called when the view is gonna be reloaded.
 * @param onLoadMore Function() The function to be called when more channels need to be loaded.
 */
@Composable
internal fun ScrollingView(
    modifier: Modifier = Modifier,
    chats: Scrolling,
    onLogout: () -> Unit = {},
    onInfoClick: (ChannelBasicInfo) -> Unit = {},
    onReload: () -> Unit = {},
    onChannelClick: (ChannelBasicInfo) -> Unit = {},
    onDeleteOrLeave: (ChannelBasicInfo) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    val channels by chats.channels.collectAsState(emptyList())
    val hasMore by chats.hasMore.collectAsState(false)
    Column(
        modifier = modifier.testTag(CHANNEL_SCROLLING_VIEW),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScrollHeader(R.string.my_chats, onLogout)
        SwipeRefresh(
            state = SwipeRefreshState(false),
            onRefresh = {
                onReload()
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = channels,
                    key = { _: Int, channel: ChannelBasicInfo -> channel.cId.toInt() },
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
                                    .width(ACTION_LIST_WIDTH.dp)
                                    .fillParentMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    ActionIcon(
                                        modifier = Modifier
                                            .fillParentMaxHeight()
                                            .weight(1f)
                                            .testTag(DELETE_OR_LEAVE_ICON_TAG)
                                            .width(ACTION_ICON_WIDTH.dp),
                                        icon = Icons.Default.Delete,
                                        backgroundColor = MaterialTheme.colorScheme.error,
                                        onClick = { onDeleteOrLeave(channel) },
                                        contentDescription = "Delete or leave a channel"
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxHeight(),
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    ActionIcon(
                                        modifier = Modifier
                                            .fillParentMaxHeight()
                                            .width(ACTION_ICON_WIDTH.dp)
                                            .testTag(INFO_ICON_TAG)
                                            .padding(end = ACTION_ICON_PADDING.dp),
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
                                .background(Color.Transparent),
                            chatItem = channel,
                            buttonModifier = Modifier.testTag(CHANNEL_BUTTON_TAG),
                            buttonString = stringResource(R.string.enter_channels),
                            onClick = { onChannelClick(channel) }
                        )
                    }
                }
                item(
                    key = hasMore
                ) {
                    if (hasMore) {
                        LoadMoreIcon(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(LIST_ITEM_HEIGHT.dp),
                            onVisible = onLoadMore
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IdleViewPreview() {
    val chats =
        Scrolling(
            flowOf(
                List(27) {
                    ChannelBasicInfo(
                        cId = it.toUInt(),
                        name = ChannelName("Channel $it"),
                        owner = UserInfo(it.toUInt(), "Owner $it")
                    )
                }
            ),
            flowOf(true)
        )
    ScrollingView(
        modifier = Modifier.fillMaxSize(),
        chats = chats
    )
}