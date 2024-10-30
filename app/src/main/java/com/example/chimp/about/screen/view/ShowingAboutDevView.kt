package com.example.chimp.about.screen.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.about.model.Dev
import com.example.chimp.about.screen.composable.AboutDeveloper
import com.example.chimp.about.viewModel.state.AboutScreenState
import java.net.URL

const val SHOWING_ABOUT_DEV_VIEW_TAG = "ShowingAboutDevView"
private const val SPACE_BETWEEN_DEVS = 4

@Composable
fun ShowingAboutDevView(
    modifier: Modifier = Modifier,
    state: AboutScreenState.Showing,
    onShowDialogChange: (Dev) -> Unit = { },
    onIsExpandedChange: (Dev) -> Unit = { },
    onIdleChange: () -> Unit = { },
    ) {
    Column(
        modifier = modifier
            .testTag(SHOWING_ABOUT_DEV_VIEW_TAG),
        verticalArrangement = spacedBy(SPACE_BETWEEN_DEVS.dp)
    ) {
        val context = LocalContext.current
        AboutScreenState.devs.forEach { dev ->
            if(dev == state.dev) {
                AboutDeveloper(
                    modifier = Modifier
                        .fillMaxWidth(),
                    dev = dev,
                    isExpanded = true,
                    showDialog = false,
                    gitOnClick = makeLink(dev.socialMedia?.gitHub) { uri ->
                        state.linkActivity(uri, context)
                    },
                    linkedInOnClick = makeLink(dev.socialMedia?.linkedIn) { uri ->
                        state.linkActivity(uri, context)
                    },
                    emailOnClick = { state.sendActivity(dev.email.email, context) },
                    onShowDialogChange = { onShowDialogChange(dev) },
                    onIsExpandedChange = onIdleChange
                )
            } else {
                AboutDeveloper(
                    modifier = Modifier
                        .fillMaxWidth(),
                    dev = dev,
                    isExpanded = false,
                    showDialog = false,
                    onIsExpandedChange = { onIsExpandedChange(dev) }
                )
            }

        }
    }




}

/**
 * Creates a function that opens a link when clicked.
 * @param uri [URL] The URL to be opened.
 * @param func ([Uri]) -> Unit The function to be called when the link is clicked.
 * @return () -> Unit The function that opens the link.
 */
private fun makeLink(uri: URL?, func: (Uri) -> Unit): () -> Unit {
    return if (uri != null) {
        { func(Uri.parse(uri.toString())) }
    } else {
        { }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowingAboutDevViewPreview() {
    ShowingAboutDevView(
        state = AboutScreenState.Showing(AboutScreenState.devs.first())
    )
}
