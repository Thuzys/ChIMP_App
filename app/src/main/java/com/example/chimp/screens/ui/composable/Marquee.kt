package com.example.chimp.screens.ui.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.isActive

private const val SPACE_BETWEEN_REPETITIONS = 16

/**
 * Marquee is an implementation of marquee effect from Android XML for JetpackCompose
 *
 * @param modifier Adjust the drawing layout or drawing decoration of the content.
 * @param params Params which specify appearance of marquee effect
 * @param content Composable content that will be applied marquee effect.
 */
@Composable
fun Marquee(
    modifier: Modifier = Modifier,
    params: MarqueeParams = defaultMarqueeParams(),
    content: @Composable (Modifier) -> Unit
) {
    var xOffset by remember { mutableIntStateOf(0) }
    val layoutInfoState = remember { mutableStateOf<MarqueeLayoutInfo?>(null) }

    LaunchedEffect(layoutInfoState.value) {
        val ltr = params.direction == LayoutDirection.Ltr

        val layoutInfo = layoutInfoState.value ?: return@LaunchedEffect
        if (layoutInfo.width <= layoutInfo.containerWidth) return@LaunchedEffect

        val duration = params.period * (layoutInfo.width + SPACE_BETWEEN_REPETITIONS) / layoutInfo.containerWidth

        val animation = TargetBasedAnimation(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration, easing = params.easing
                ), repeatMode = RepeatMode.Restart
            ),
            typeConverter = Int.VectorConverter,
            initialValue = if (ltr) 0 else -(layoutInfo.width + SPACE_BETWEEN_REPETITIONS),
            targetValue = if (ltr) -(layoutInfo.width + SPACE_BETWEEN_REPETITIONS) else 0
        )

        val startTime = withFrameNanos { it }

        do {
            val playTime = withFrameNanos { it } - startTime
            xOffset = animation.getValueFromNanos(playTime)
        } while (isActive)
    }

    SubcomposeLayout(
        modifier = modifier.clipToBounds()
    ) { constraints ->
        val infiniteWidthConstraints = constraints.copy(maxWidth = Int.MAX_VALUE)

        var main = subcompose(MarqueeLayers.Main) {
            content(Modifier)
        }.first().measure(infiniteWidthConstraints)

        var secondPlaceableWithOffset: Pair<Placeable, Int>? = null

        if (main.width <= constraints.maxWidth) {
            main = subcompose(MarqueeLayers.Secondary) {
                content(Modifier.fillMaxWidth())
            }.first().measure(constraints)
            layoutInfoState.value = null
        } else {
            layoutInfoState.value = MarqueeLayoutInfo(
                width = main.width, containerWidth = constraints.maxWidth
            )
            val secondTextOffset = main.width + SPACE_BETWEEN_REPETITIONS + xOffset

            if (secondTextOffset < constraints.maxWidth) {
                secondPlaceableWithOffset = subcompose(MarqueeLayers.Secondary) {
                    content(Modifier)
                }.first().measure(infiniteWidthConstraints) to secondTextOffset
            }
        }

        layout(
            width = constraints.maxWidth, height = main.height
        ) {
            main.place(xOffset, 0)
            secondPlaceableWithOffset?.let {
                it.first.place(it.second, 0)
            }
        }
    }
}

data class MarqueeParams(
    val period: Int = 7500,
    val gradientEnabled: Boolean,
    val gradientEdgeColor: Color,
    val direction: LayoutDirection,
    val easing: Easing
)

@Composable
fun defaultMarqueeParams(): MarqueeParams = MarqueeParams(
    period = 7500,
    gradientEnabled = true,
    gradientEdgeColor = Color.White,
    direction = LocalLayoutDirection.current,
    easing = LinearEasing
)

private enum class MarqueeLayers {
    Main,
    Secondary
}

private data class MarqueeLayoutInfo(
    val width: Int,
    val containerWidth: Int
)