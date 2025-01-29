package com.example.chimp.screens.channel.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.time.formatTime
import com.example.chimp.screens.channel.screen.composable.ChatType.RECEIVED
import com.example.chimp.screens.channel.screen.composable.ChatType.SENT
import java.sql.Timestamp

private const val SIZE = 40
private const val TEXT_END_PADDING = 8
private const val COLUMN_PADDING = 8
private const val COLUMN_END_PADDING = 48
private const val COLUMN_TEXT_PADDING = 16

@Composable
internal fun MakeMessage(
    message: Message,
    owner: UserInfo,
) {
    if (message.owner.id != owner.id) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier.align(Alignment.Bottom),
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    modifier = Modifier
                        .size(SIZE.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
                Text(
                    text = message.owner.name,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(end = TEXT_END_PADDING.dp)
                )
            }
            Column {
                Box(
                    modifier = Modifier
                        .padding(COLUMN_PADDING.dp)
                        .padding(end = COLUMN_END_PADDING.dp)
                        .clip(BubbleShape(RECEIVED))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = message.message,
                        modifier = Modifier.padding(COLUMN_TEXT_PADDING.dp)
                    )
                }
                message.time?.let {
                    Text(
                        text = message.time.formatTime(),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Column(
                modifier = Modifier.align(Alignment.Bottom),
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .padding(COLUMN_PADDING.dp)
                        .padding(start = COLUMN_END_PADDING.dp)
                        .clip(BubbleShape(chatType = SENT))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = message.message,
                        modifier = Modifier.padding(COLUMN_TEXT_PADDING.dp)
                    )
                }
                message.time?.let {
                    Text(
                        text = message.time.formatTime(),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MakeMessagePreview() {
    Column {
        MakeMessage(
            message = Message(
                cId = 1u,
                message = "Hello",
                owner = UserInfo(1u, "User 1"),
                time = Timestamp(System.currentTimeMillis())
            ),
            owner = UserInfo(1u, "User 1")
        )
        MakeMessage(
            message = Message(
                cId = 1u,
                message = "Hi",
                owner = UserInfo(2u, "User 2"),
                time = Timestamp(System.currentTimeMillis())
            ),
            owner = UserInfo(1u, "User 1")
        )
    }
}