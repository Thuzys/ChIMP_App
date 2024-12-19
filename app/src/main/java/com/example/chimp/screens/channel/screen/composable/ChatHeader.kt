package com.example.chimp.screens.channel.screen.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo

/**
 * ChatHeader is a composable that displays the header of the scrolling view.
 *
 * @param onBackClick the callback to be called when the back button is clicked.
 * @param channel the channel to display.
 * @param onInfoClick the callback to be called when the info button is clicked.
 */
@Composable
internal fun ChatHeader(
    onBackClick: () -> Unit,
    channel: ChannelBasicInfo,
    onInfoClick: (ChannelBasicInfo) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                contentDescription = "Back"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp)),
            painter = painterResource(id = channel.icon),
            contentDescription = "channel Picture"
        )
        Text(
            text = channel.name.displayName,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onInfoClick(channel) },
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatHeaderPreview() {
    ChatHeader(
        onBackClick = {},
        channel = ChannelBasicInfo(
            cId = 1u,
            name = ChannelName("Channel 1", "Channel 1"),
            owner = UserInfo(1u, "Owner 1"),
        ),
        onInfoClick = {}
    )
}