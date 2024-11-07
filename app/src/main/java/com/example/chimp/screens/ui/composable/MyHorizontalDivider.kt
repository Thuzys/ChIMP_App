package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val darkThemeColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.12f)
private val lightThemeColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.12f)

@Composable
fun MyHorizontalDivider(
    modifier: Modifier = Modifier,
) {
    if (isSystemInDarkTheme()) {
        androidx.compose.material3.HorizontalDivider(
            modifier = modifier,
            color = darkThemeColor,
        )
    } else {
        androidx.compose.material3.HorizontalDivider(
            modifier = modifier,
            color = lightThemeColor,
        )
    }
}