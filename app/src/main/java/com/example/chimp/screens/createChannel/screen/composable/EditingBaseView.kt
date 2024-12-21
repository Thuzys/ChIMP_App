package com.example.chimp.screens.createChannel.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.screens.ui.composable.GradientBox

const val EDITING_BASE_VIEW_TAG = "EditingBaseView"

const val EDITING_BASE_VIEW_CONTENT_TAG = "EditingBaseViewContent"

/**
 * The radius of the rounded corners of the box.
 */
private const val ROUND_CORNER_RADIUS = 40

private const val BOX_HEIGHT = 0.6f
@Composable
internal fun EditingBaseView(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.(Boolean) -> Unit),
) {
    GradientBox(
        modifier = modifier.testTag(EDITING_BASE_VIEW_TAG),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center // Center the content vertically
        ) {}
        Column(
            modifier = modifier
                .testTag(EDITING_BASE_VIEW_CONTENT_TAG)
                .fillMaxWidth()
                .fillMaxHeight(BOX_HEIGHT)
                .clip(
                    RoundedCornerShape(
                        topStart = ROUND_CORNER_RADIUS.dp,
                        topEnd = ROUND_CORNER_RADIUS.dp,
                        bottomEnd = ROUND_CORNER_RADIUS.dp,
                        bottomStart = ROUND_CORNER_RADIUS.dp
                    )
                )
                .background(Color.White)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content(true)
        }
    }
}


@Preview
@Composable
private fun BaseViewPreview() {
    EditingBaseView { _ -> Text("Hello, World!") }
}
