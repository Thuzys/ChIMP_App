package com.example.chimp.screens.chats.screen.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.screens.ui.composable.GradientBox
import com.example.chimp.screens.ui.utils.rememberImeState

const val CHAT_BASE_VIEW_TAG = "ChatBaseView"

/**
 * The height of the top box as a percentage of the screen height.
 */
private const val HEIGHT_PERCENTAGE = 0.90f

/**
 * The radius of the rounded corners of the box.
 */
private const val ROUND_CORNER_RADIUS = 35

const val CHAT_BASE_VIEW_CONTENT_TAG = "ChatBaseViewContent"

/**
 * Base view for the chat screen.
 *
 * @param modifier Modifier to be applied to the view.

 * @param content Content to be displayed in the view.
 */
@Composable
internal fun ChatBaseView(
    modifier: Modifier = Modifier,
    channelName: ChannelName
) {
    GradientBox(
        modifier = modifier.testTag(CHAT_BASE_VIEW_TAG),
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val isImeVisible by rememberImeState()
            val animatedUpperSectionRation by animateFloatAsState(
                targetValue = if (isImeVisible) 0f else HEIGHT_PERCENTAGE,
                label = "Upper Section Ratio"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.1f)
                    .background(Color.LightGray)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "   " + channelName.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,

                    )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(animatedUpperSectionRation),
                contentAlignment = Alignment.Center
            ) {
            }
            Column(
                modifier = Modifier
                    .testTag(CHAT_BASE_VIEW_CONTENT_TAG)
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = ROUND_CORNER_RADIUS.dp,
                            topEnd = ROUND_CORNER_RADIUS.dp,
                            bottomStart = ROUND_CORNER_RADIUS.dp,
                            bottomEnd = ROUND_CORNER_RADIUS.dp,
                        )
                    )
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun ChatBaseViewPreview() {
    ChatBaseView(
        channelName = ChannelName("channel")
    )
}
