package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

const val ACTION_ICON_TAG = "ActionIcon"

@Composable
fun ActionIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: ImageVector,
    contentDescription: String? = null,
    tint: Color = Color.White,
    onClick: () -> Unit = {},
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .testTag(ACTION_ICON_TAG)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview
@Composable
private fun ActionIconPreview() {
    ActionIcon(
        onClick = {},
        backgroundColor = Color.Red,
        icon = Icons.Default.Delete
    )
}