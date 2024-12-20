package com.example.chimp.screens.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.screens.ui.composable.GradientBox
import com.example.chimp.screens.ui.composable.MakeButton

/**
 * The tag for the error view.
 */
const val ERROR_VIEW_TEST_TAG = "ErrorView"

/**
 * The tag for the error button.
 */
const val ERROR_BUTTON_TEST_TAG = "ErrorButton"

/**
 * The radius of the rounded corners of the column.
 */
private const val COLUMN_CONNER_RADIUS = 16

/**
 * The padding of the button.
 */
private const val BUTTON_PADDING = 16

@Composable
internal fun ErrorView(
    modifier: Modifier,
    tryAgain: () -> Unit = {},
    error: ResponseError
) {
    GradientBox(
        colors = listOf(
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError,
        ),
        modifier = modifier.testTag(ERROR_VIEW_TEST_TAG),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(0.33f)
                    .wrapContentHeight()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = COLUMN_CONNER_RADIUS.dp,
                            topEnd = COLUMN_CONNER_RADIUS.dp,
                            bottomStart = COLUMN_CONNER_RADIUS.dp,
                            bottomEnd = COLUMN_CONNER_RADIUS.dp
                        )
                    )
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .wrapContentHeight()
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = error.cause,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                    )
                }
                MakeButton(
                    modifier = Modifier
                        .testTag(ERROR_BUTTON_TEST_TAG)
                        .padding(BUTTON_PADDING.dp),
                    color = MaterialTheme.colorScheme.error,
                    text = stringResource(R.string.try_again),
                    onClick = tryAgain,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorViewPreview() {
    ErrorView(
        modifier = Modifier.fillMaxSize(),
        error = ResponseError("Error", "https://www.google.com")
    )
}