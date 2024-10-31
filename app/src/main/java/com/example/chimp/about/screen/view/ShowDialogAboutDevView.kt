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

const val SHOW_DIALOG_ABOUT_DEV_VIEW_TAG = "ShowDialogAboutDevView"
private const val SPACE_BETWEEN_DEVS = 4

/**
 * The composable function that displays the dialog view.
 *
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param state [AboutScreenState.ShowDialog] The state of the dialog.
 * @param onShowingChange ([Dev]) -> Unit The function to be called when the user change states.
 */
@Composable
fun ShowDialogAboutDevView(
    modifier: Modifier = Modifier,
    state: AboutScreenState.ShowDialog,
    onShowingChange: (Dev) -> Unit = {},
) {
    Column(
        modifier = modifier
            .testTag(SHOW_DIALOG_ABOUT_DEV_VIEW_TAG),
        verticalArrangement = spacedBy(SPACE_BETWEEN_DEVS.dp)
    ) {
        AboutScreenState
            .devs
            .forEach { dev ->
                if (dev == state.dev) {
                    AboutDeveloper(
                        modifier = Modifier
                            .fillMaxWidth(),
                        dev = dev,
                        isExpanded = true,
                        showDialog = true,
                        onShowDialogChange = { onShowingChange(dev) },
                    )
                } else {
                    AboutDeveloper(
                        modifier = Modifier
                            .fillMaxWidth(),
                        dev = dev,
                        isExpanded = false,
                        showDialog = false,
                    )
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowDialogAboutDevView() {
    ShowDialogAboutDevView(
        state = AboutScreenState.ShowDialog(AboutScreenState.devs.first())
    )
}