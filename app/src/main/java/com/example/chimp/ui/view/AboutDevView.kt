package com.example.chimp.ui.view

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chimp.model.about.About
import com.example.chimp.ui.composable.AboutDeveloper
import com.example.chimp.viewModel.state.ChimpState
import java.net.URL

/**
 * The composable function that displays the developer's information view.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param state [ChimpState.AboutDevState] The state of the developers.
 * @param onShowDialogChange
 * ([About]) -> Unit The function to be called when the user clicks on the email icon.
 * @param onIsExpandedChange
 * ([About]) -> Unit The function to be called when the user clicks on the GitHub icon.
 */
@Composable
fun AboutDevView(
    modifier: Modifier = Modifier,
    state: ChimpState.AboutDevState,
    onShowDialogChange: (About) -> Unit,
    onIsExpandedChange: (About) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        state
            .aboutSelectorsList
            .forEach { (dev, devState) ->
                val (isExpanded, showDialog) = devState
                val git = dev.socialMedia?.gitHub
                val linkedIn = dev.socialMedia?.linkedIn
                val gitOnClick: () -> Unit = makeLink(git, state::linkMaker)
                val linkedInOnClick: () -> Unit = makeLink(linkedIn, state::linkMaker)
                val emailOnClick = { state.sendMaker(dev.email.email) }
                AboutDeveloper(
                    modifier = Modifier
                        .fillMaxWidth(),
                    dev = dev,
                    isExpanded = isExpanded,
                    showDialog = showDialog,
                    gitOnClick = gitOnClick,
                    linkedInOnClick = linkedInOnClick,
                    emailOnClick = emailOnClick,
                    onShowDialogChange = { onShowDialogChange(dev) },
                    onIsExpandedChange = { onIsExpandedChange(dev) }
                )
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
