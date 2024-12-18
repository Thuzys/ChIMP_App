package com.example.chimp.screens.channels.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R

/**
 * The tag for the logout icon.
 */
const val LOGOUT_ICON_TAG = "LogoutIcon"


/**
 * The padding for the header.
 */
private const val HEADER_PADDING = 16

/**
 * The clip radius for the header.
 */
private const val HEADER_CLIP_RADIUS = 8

/**
 * The size of the logout icon.
 */
private const val LOGOUT_ICON_SIZE = 24

/**
 * The padding for the logout icon.
 */
private const val LOGOUT_ICON_PADDING = 8

/**
 * The header for the channels scroll.
 *
 * @param logout The logout action.
 */
@Composable
internal fun ChannelsScrollHeader(logout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape =
                RoundedCornerShape(
                    bottomEnd = HEADER_CLIP_RADIUS.dp,
                    bottomStart = HEADER_CLIP_RADIUS.dp
                )
            )
            .background(MaterialTheme.colorScheme.onSurface)
            .padding(HEADER_PADDING.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.my_chats),
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.weight(0.7f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.TwoTone.ExitToApp,
            contentDescription = "Logout",
            tint = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .testTag(LOGOUT_ICON_TAG)
                .padding(LOGOUT_ICON_PADDING.dp)
                .width(LOGOUT_ICON_SIZE.dp)
                .height(LOGOUT_ICON_SIZE.dp)
                .clickable(onClick = logout),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChannelsScrollHeaderPreview() {
    ChannelsScrollHeader(logout = {})
}