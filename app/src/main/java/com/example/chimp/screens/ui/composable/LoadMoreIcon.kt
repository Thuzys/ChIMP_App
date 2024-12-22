package com.example.chimp.screens.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.chimp.screens.ui.views.LoadingView

/**
 * The tag for the LoadMoreIcon.
 */
const val LOAD_MORE_ICON_TAG = "LoadMoreIcon"

/**
 * A composable that shows a loading icon when the user scrolls to the bottom of the list.
 *
 * @param modifier The modifier to apply to this layout.
 * @param onVisible The callback to be called when the icon is visible.
 */
@Composable
fun LoadMoreIcon(
    modifier: Modifier = Modifier,
    onVisible: () -> Unit = {},
) {
    var itemBounds by remember { mutableStateOf<Rect?>(null) }
    val view = LocalView.current

    LaunchedEffect(itemBounds) {
        itemBounds?.let { bounds ->
            val windowBounds = Rect(
                0f,
                0f,
                view.width.toFloat(),
                view.height.toFloat()
            )
            if (bounds.overlaps(windowBounds)) onVisible()
        }
    }
    Box(
        modifier = modifier
            .testTag(LOAD_MORE_ICON_TAG)
            .onGloballyPositioned { coordinates ->
                itemBounds = coordinates.boundsInWindow()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            LoadingView(Modifier.fillMaxSize())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadMoreIconPreview() {
    LoadMoreIcon()
}