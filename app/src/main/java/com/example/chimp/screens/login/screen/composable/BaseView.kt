package com.example.chimp.screens.login.screen.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.ui.composable.GradientBox
import com.example.chimp.screens.ui.utils.rememberImeState

const val BASE_VIEW_TAG = "BaseView"

/**
 * The height of the top box as a percentage of the screen height.
 */
private const val HEIGHT_PERCENTAGE = 0.35f

/**
 * The radius of the rounded corners of the box.
 */
private const val ROUND_CORNER_RADIUS = 40

const val BASE_VIEW_CONTENT_TAG = "BaseViewContent"

/**
 * Base view for the login screen.
 *
 * @param modifier Modifier to be applied to the view.
 * @param content Content to be displayed in the view.
 */
@Composable
internal fun BaseView(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.(Boolean) -> Unit),
) {
    GradientBox(
        modifier = modifier.testTag(BASE_VIEW_TAG),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val isImeVisible by rememberImeState()
            val animatedUpperSectionRation by animateFloatAsState(
                targetValue = if (isImeVisible) 0f else HEIGHT_PERCENTAGE,
                label = "Upper Section Ratio"
            )
            AnimatedVisibility(
                visible = !isImeVisible
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(animatedUpperSectionRation),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.welcome_message),
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .testTag(BASE_VIEW_CONTENT_TAG)
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = ROUND_CORNER_RADIUS.dp,
                            topEnd = ROUND_CORNER_RADIUS.dp
                        )
                    )
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                content(isImeVisible)
            }
        }
    }
}

@Preview
@Composable
private fun BaseViewPreview() {
    BaseView{ _ -> Text("Hello, World!") }
}
