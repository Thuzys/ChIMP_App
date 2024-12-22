package com.example.chimp.screens.createChannel.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

const val EDITING_BASE_VIEW_TAG = "EditingBaseView"

const val EDITING_BASE_VIEW_CONTENT_TAG = "EditingBaseViewContent"


@Composable
internal fun EditingBaseView(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.(Boolean) -> Unit),
) {
    Column(
        modifier = modifier
            .testTag(EDITING_BASE_VIEW_CONTENT_TAG)
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        content(true)
    }
}


@Preview
@Composable
private fun BaseViewPreview() {
    EditingBaseView { _ -> Text("Hello, World!") }
}
