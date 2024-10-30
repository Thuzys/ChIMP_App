package com.example.chimp.about.screen.view

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.about.model.Dev
import com.example.chimp.about.screen.composable.AboutDeveloper
import com.example.chimp.about.viewModel.state.AboutScreenState

const val IDLE_ABOUT_DEV_VIEW_TAG = "IdleAboutDevView"
private const val SPACE_BETWEEN_DEVS = 4

/**
 * Composable that displays the list of developers in the About screen.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param onIsExpandedChange Callback to be called when the expanded state of a developer changes.
 */
@Composable
fun IdleAboutDevView(
    modifier: Modifier = Modifier,
    onIsExpandedChange: (Dev) -> Unit = {},
) {
    Column(
        modifier = modifier.testTag(IDLE_ABOUT_DEV_VIEW_TAG),
        verticalArrangement = spacedBy(SPACE_BETWEEN_DEVS.dp)
    ) {
        AboutScreenState
            .devs
            .forEach { dev ->
                AboutDeveloper(
                    modifier = Modifier.fillMaxWidth(),
                    dev = dev,
                    onIsExpandedChange = { onIsExpandedChange(dev) }
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdleAboutDevViewPreview() {
    IdleAboutDevView()
}