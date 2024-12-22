package com.example.chimp.screens.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * A value that represents the screen height in dp that is considered small.
 */
private const val DP_COMPARISON = 790
@Composable
fun isSmallScreen(): Boolean = LocalConfiguration.current.screenHeightDp < DP_COMPARISON
