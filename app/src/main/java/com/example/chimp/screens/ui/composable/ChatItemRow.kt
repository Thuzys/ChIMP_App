package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelItem

/**
 * The threshold for the length of the text to determine if marquee effect should be applied.
 */
private const val TEXT_LENGTH_THRESHOLD = 15

/**
 * ChatItemRow is a composable that displays a row for a chat item.
 *
 * @param chatItem the chat item to display.
 */
@Composable
fun ChatItemRow(
    modifier: Modifier = Modifier,
    chatItem: ChannelBasicInfo,
    onClick: () -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = chatItem.icon),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                if (chatItem.name.name.length < TEXT_LENGTH_THRESHOLD) {
                    Text(
                        text = chatItem.name.name,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        modifier = Modifier.wrapContentWidth()
                    )
                } else {
                    Marquee {
                        Text(
                            text = chatItem.name.name,
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 1,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
            }
            MakeButton(
                modifier = Modifier.width(12.dp),
                text = "Join",
                onClick = onClick,
            )
//            MakeJoinButton { onJoin() }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun ChatItemRowPreview() {
    val item = ChannelBasicInfo(1u, ChannelName("One Piece Fansssssssssss"), R.drawable.thuzy_profile_pic)
    ChatItemRow(chatItem = item) { }
}