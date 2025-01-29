package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo

/**
 * The threshold for the length of the text to determine if marquee effect should be applied.
 */
private const val TEXT_LENGTH_THRESHOLD = 15
private const val PADDING = 16
private const val IMAGE_SIZE = 64
private const val MAX_LINES = 1
private const val DEFAULT_ELEVATION = 4

/**
 * ChatItemRow is a composable that displays a row for a chat item.
 *
 * @param chatItem the chat item to display.
 */
@Composable
fun ChatItemRow(
    modifier: Modifier = Modifier,
    chatItem: ChannelInfo,
    buttonModifier: Modifier = Modifier.minimumInteractiveComponentSize(),
    buttonString: String,
    onClick: () -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = DEFAULT_ELEVATION.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING.dp)
        ) {
            Image(
                painter = painterResource(id = chatItem.icon),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(IMAGE_SIZE.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = PADDING.dp)
                    .weight(1f)
            ) {
                if (chatItem.name.displayName.length < TEXT_LENGTH_THRESHOLD) {
                    Text(
                        text = chatItem.name.displayName,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = MAX_LINES,
                        modifier = Modifier.wrapContentWidth()
                    )
                } else {
                    Marquee {
                        Text(
                            text = chatItem.name.displayName,
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = MAX_LINES,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
            }
            MakeButton(
                modifier = buttonModifier,
                text = buttonString,
                onClick = onClick,
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun ChatItemRowPreview() {
    val item = ChannelInfo(
        1u,
        ChannelName(
            "One Piece Fans",
            "One Piece Fansssssssssss"
        ),
        owner = UserInfo(1u, "Thuzy"),
        icon = R.drawable.thuzy_profile_pic,
    )
    ChatItemRow(chatItem = item, buttonString = "test") { }
}