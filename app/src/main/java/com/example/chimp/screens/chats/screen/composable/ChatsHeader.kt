package com.example.chimp.screens.chats.screen.composable

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
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.screens.ui.composable.MakeMark
import com.example.chimp.screens.ui.composable.MyHorizontalDivider

const val CHATS_HEADER_IMAGE_TAG = "ChatsHeaderImage"
const val CHATS_HEADER_NAME_TAG = "ChatsHeaderName"
const val CHATS_HEADER_TAG = "ChatsHeader"

@Composable
internal fun ChatsHeader(
    modifier: Modifier = Modifier,
    chat: ChannelBasicInfo,
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
                .fillMaxWidth(0.25f),
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
                    .padding(30.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.name.name,
                    style = MaterialTheme.typography.headlineMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
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
    val channel = ChannelBasicInfo(
        cId = 0u,
        name = ChannelName("Channel name with case of a very long name"),
    )
    ChatsHeader(
        chat = channel,
        onClick = {}
    )
}
