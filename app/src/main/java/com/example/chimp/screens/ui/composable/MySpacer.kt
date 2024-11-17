package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chimp.screens.ui.utils.isSmallScreen

/**
 * The spacing between the top and bottom boxes.
 */
private const val SMALL_SCREEN_SPACING = 0.05f

/**
 * The spacing between the top and bottom boxes.
 */
private const val LARGE_SCREEN_SPACING = 0.1f

/**
 * A spacer that adjusts its height based on the screen size.
 *
 * @param smallModifier Modifier to be applied to the spacer on small screens.
 * @param largeModifier Modifier to be applied to the spacer on large screens.
 */
@Composable
fun MySpacer(
    smallModifier: Modifier = Modifier.height(SMALL_SCREEN_SPACING.dp),
    largeModifier: Modifier = Modifier.height(LARGE_SCREEN_SPACING.dp),
) {
    if (isSmallScreen()) Spacer(modifier = smallModifier)
    else Spacer(modifier = largeModifier)
}