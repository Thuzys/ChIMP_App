package com.example.chimp.screens.ui.composable


import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.chats.screen.composable.ChatsHeader
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableRow(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    var rowWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier
                .onSizeChanged { rowWidth = it.width.toFloat() }
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(rowWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount).coerceIn(0f, rowWidth)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value >= rowWidth / 2 -> { scope.launch {
                                    offset.animateTo(rowWidth)
                                } }
                                else -> { scope.launch {
                                    offset.animateTo(0f)
                                } }
                            }
                        },
                    )
                }
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableHeaderPreview() {
    SwipeableRow(
        actions = {
            ActionIcon(
                modifier = Modifier.fillMaxHeight(),
                backgroundColor = Color.Red,
                icon = Icons.Default.Delete,
            )
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ChatsHeader(
                chat = ChannelBasicInfo(
                    cId = 0u,
                    name = ChannelName("Channel name")
                ),
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeableRowPreview() {
    SwipeableRow(
        actions = {
            ActionIcon(
                modifier = Modifier.fillMaxHeight(),
                backgroundColor = Color.Red,
                icon = Icons.Default.Delete,
            )
        }
    ) {
        ChatItemRow(
            modifier = Modifier.fillMaxWidth(),
            chatItem = ChannelBasicInfo(
                cId = 0u,
                name = ChannelName("Channel name")
            ),
            onClick = {}
        )
    }
}