package com.example.chimp.screens.channels.screen.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.ui.composable.MakeMark
import com.example.chimp.screens.ui.composable.MyHorizontalDivider

const val CHATS_HEADER_IMAGE_TAG = "ChatsHeaderImage"
const val CHATS_HEADER_TAG = "ChatsHeader"

private const val MAX_WIDTH = 0.25f
private const val PADDING = 30
private const val MAX_LINES = 1

@Composable
internal fun ChatsHeader(
    modifier: Modifier = Modifier,
    chat: ChannelInfo,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag(CHATS_HEADER_TAG),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MakeMark(
            modifier = Modifier
                .testTag(CHATS_HEADER_IMAGE_TAG)
                .fillMaxWidth(MAX_WIDTH),
            lightMode = R.drawable.user_mark,
            contentDescription = "Channel image",
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PADDING.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.name.displayName,
                    style = MaterialTheme.typography.headlineMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = MAX_LINES
                )
            }
            MyHorizontalDivider()
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ChatsHeaderPreview() {
    val channel = ChannelInfo(
        cId = 0u,
        name = ChannelName(
            "Channel name",
            "Channel name with case of a very long name"
        ),
        owner = UserInfo(1u, "Owner name"),
    )
    ChatsHeader(
        chat = channel,
        onClick = {}
    )
}
