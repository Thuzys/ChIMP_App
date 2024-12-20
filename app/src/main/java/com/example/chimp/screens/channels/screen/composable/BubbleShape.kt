package com.example.chimp.screens.channels.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal enum class ChatType {
    SENT,
    RECEIVED
}

private const val DEFAULT_CLIP_CORNER = 16f
private const val DEFAULT_TIP_SIZE = 1f

internal class BubbleShape(
    private val chatType: ChatType = ChatType.RECEIVED,
    private val cornerRadius: Dp = DEFAULT_CLIP_CORNER.dp,
    private val tipSize: Dp = DEFAULT_TIP_SIZE.dp,
): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = with(density) { cornerRadius.toPx() }
        val tipSize = with(density) { tipSize.toPx() }
        val shape = Path().apply{
            when(chatType) {
                ChatType.RECEIVED -> {
                    addRoundRect(
                        RoundRect(
                            left = tipSize,
                            top = 0f,
                            right = size.width,
                            bottom = size.height - tipSize,
                            radiusX = cornerRadius,
                            radiusY = cornerRadius
                        )
                    )
                    moveTo(
                        x = tipSize,
                        y = size.height - tipSize - cornerRadius
                    )
                    lineTo(
                        x = 0f,
                        y = size.height
                    )
                    lineTo(
                        x = tipSize + cornerRadius,
                        y = size.height - tipSize
                    )
                }
                ChatType.SENT -> {
                    addRoundRect(
                        RoundRect(
                            left = 0f,
                            top = 0f,
                            right = size.width - tipSize,
                            bottom = size.height - tipSize,
                            radiusX = cornerRadius,
                            radiusY = cornerRadius
                        )
                    )
                    moveTo(
                        x = size.width - tipSize,
                        y = size.height - cornerRadius - tipSize
                    )
                    lineTo(
                        x = size.width - (cornerRadius),
                        y = size.height - tipSize
                    )
                    lineTo(
                        x = size.width,
                        y = size.height
                    )
                }
            }
            close()
        }
        return Outline.Generic(shape)
    }

}

@Preview
@Composable
private fun BubbleShapePreview() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(BubbleShape(ChatType.SENT))
            .background(Color.Red)
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = "Hello, World!",
        )
    }
}

@Preview
@Composable
private fun BubbleShapeReceivedPreview() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(BubbleShape(ChatType.RECEIVED))
            .background(Color.Blue)
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = "Hello, World!",
        )
    }
}

