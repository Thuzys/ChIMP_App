package com.example.chimp.screens.channels.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.chimp.models.users.User
import com.example.chimp.screens.channel.model.Message

@Composable
internal fun MakeMessage(
    message: Message,
    owner: User,
) {
    if (message.owner.id == owner.id) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Bottom)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
                Text(
                    text = message.owner.name,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(end = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(BubbleShape())
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = message.message,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(8.dp)
                    .clip(BubbleShape(ChatType.SENT))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Row {
                    Text(
                        text = message.message,
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    )
                }
            }
//            Column(
//                modifier = Modifier
//                    .align(Alignment.Bottom)
//            ) {
//                Icon(
//                    modifier = Modifier
//                        .size(40.dp),
//                    imageVector = Icons.Default.AccountCircle,
//                    contentDescription = null
//                )
//                Text(
//                    text = message.owner.name,
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(end = 8.dp)
//                )
//            }
        }
    }
}