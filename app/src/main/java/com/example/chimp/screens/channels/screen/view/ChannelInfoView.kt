package com.example.chimp.screens.channels.screen.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channels.model.channel.ChannelInfo
import com.example.chimp.screens.ui.composable.MyHorizontalDivider
import com.example.chimp.screens.ui.composable.ShowDialog

/**
 * The tag used for the channel's information view.
 */
const val CHANNEL_INFO_VIEW_TAG = "ChannelInfoView"

/**
 * The tag used for the channel's description.
 */
const val CHANNEL_DESCRIPTION_TAG = "ChannelDescription"

/**
 * The tag used for the channel's small description.
 */
const val CHANNEL_INFO_SMALL_DESCRIPTION_TAG = "ChannelInfoSmallDescription"

/**
 * The tag used for the back button.
 */
const val CHANNEL_INFO_BACK_BUTTON = "ChannelInfoBackButton"

/**
 * The image size used for the channel's picture.
 */
private const val IMAGE_SIZE = 200

/**
 * The padding used for the developer's profile text.
 */
private const val PADDING = 16

/**
 * The maximum number of lines for the biography.
 */
private const val MAX_LINES = 2

/**
 * The view that displays the channel's information.
 *
 * @param modifier The modifier to apply to this layout.
 * @param channel The channel's information.
 * @param onGoBackClick The action to perform when the user clicks on the back button.
 */
@Composable
fun ChannelInfoView(
    modifier: Modifier = Modifier,
    channel: ChannelInfo,
    onGoBackClick: () -> Unit = {},
) {
    var showDescription by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .testTag(CHANNEL_INFO_VIEW_TAG)
            .padding(top = PADDING.dp)
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .clickable(onClick = onGoBackClick)
                    .testTag(CHANNEL_INFO_BACK_BUTTON)
                    .padding(PADDING.dp)
            )
        }
        Image(
            painter = painterResource(id = channel.icon),
            contentDescription = "Channel icon",
            modifier = Modifier
                .size(IMAGE_SIZE.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraLarge)
        )
        Text(
            text = channel.name.name,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        MyHorizontalDivider()
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(PADDING.dp)
                .testTag(CHANNEL_INFO_SMALL_DESCRIPTION_TAG)
                .clickable(onClick = { showDescription = true }),
            text = channel.description ?: "No description available.",
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = MAX_LINES
        )
        ShowDialog(
            showDialog = showDescription,
            onDismissRequest = { showDescription = false }
        ) {
            Text(
                modifier =
                Modifier
                    .testTag(CHANNEL_DESCRIPTION_TAG)
                    .clickable(onClick = { showDescription = false }),
                text = channel.description ?: "No biography available.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChannelInfoViewPreview() {
    val channel = ChannelInfo(
        cId = 1u,
        name = ChannelName("Channel 1"),
        description = "Channel 1 description",
        owner = UserInfo(id = 1u, name = "Owner 1")
    )
    ChannelInfoView(
        modifier = Modifier.fillMaxSize(),
        channel = channel
    )
}