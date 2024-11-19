package com.example.chimp.screens.chats.screen.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo

@Composable
internal fun ChatHeader(
    onBackClick: () -> Unit,
    channelBasicInfo: ChannelBasicInfo,
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
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Picture"
        )
        Text(
            text = channelBasicInfo.name.name,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = { onInfoClick(channelBasicInfo) },
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Options"
            )
        }
    }
}