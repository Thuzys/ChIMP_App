package com.example.chimp.about.screen.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.chimp.about.model.Dev
import com.example.chimp.about.model.Email
import com.example.chimp.about.model.SocialMedia
import com.example.chimp.ui.composable.Header
import java.net.URL

const val ABOUT_DEVELOPER_TAG = "AboutDeveloper"
const val ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG = "AboutDeveloperIsExpanded"
private const val IS_EXPANDED_PADDING = 16
private const val NOT_EXPANDED_PADDING = 8
/**
 * The composable function that displays the developer's information.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param dev [Dev] The developer's information.
 * @param isExpanded Boolean The state of the developer's profile.
 * @param gitOnClick
 * () -> Unit The function to be called when the user clicks on the GitHub icon.
 * @param linkedInOnClick
 * () -> Unit The function to be called when the user clicks on the LinkedIn icon.
 * @param emailOnClick
 * () -> Unit The function to be called when the user clicks on the email icon.
 * @param onShowDialogChange
 * () -> Unit The function to be called when the user clicks on the dev's bio.
 * @param onIsExpandedChange
 * () -> Unit The function to be called when the user clicks on the dev's profile.
 */
@Composable
fun AboutDeveloper(
    modifier: Modifier = Modifier,
    dev: Dev,
    isExpanded: Boolean = false,
    showDialog: Boolean = false,
    gitOnClick: () -> Unit = {},
    linkedInOnClick: () -> Unit = {},
    emailOnClick: () -> Unit = {},
    onShowDialogChange: () -> Unit = {},
    onIsExpandedChange: () -> Unit = {},
) {
    val padding by animateDpAsState(
        targetValue = if (isExpanded) IS_EXPANDED_PADDING.dp else NOT_EXPANDED_PADDING.dp,
        label = "Card padding",
    )
    Card(
        shape = MaterialTheme.shapes.extraLarge,
        modifier = modifier
            .padding(padding)
    ) {
        Column(
            modifier = modifier.testTag(ABOUT_DEVELOPER_TAG),
        ) {
            if (!isExpanded) {
                Header(
                    modifier = Modifier
                        .testTag(ABOUT_DEVELOPER_IS_EXPANDED_ACTION_TAG)
                        .clickable(onClick = onIsExpandedChange),
                    profileId = dev.imageId,
                    profileName = dev.name,
                )
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInVertically(tween(150, 150)),
                exit = slideOutVertically(tween(150, 150)),
            ) {
                DeveloperContent(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center),
                    dev = dev,
                    showDialog = showDialog,
                    gitOnClick = gitOnClick,
                    linkedInOnClick = linkedInOnClick,
                    emailOnClick = emailOnClick,
                    onShowDialog = onShowDialogChange,
                    onIsExpanded = onIsExpandedChange,
                )
            }
        }
    }
}

private class AboutDeveloperPreviewClass : PreviewParameterProvider<Dev> {
    val email = Email("test@mail.com")
    val dev = Dev(
        name = "Arthur Oliveira",
        number = "50543",
        email = email,
        socialMedia = SocialMedia(
            gitHub = URL("https://test.com"),
            linkedIn = URL("https://test.com")
        )
    )
    override val values: Sequence<Dev> =
        sequenceOf(dev)
}


@Composable
@Preview(showBackground = true)
private fun AboutDeveloperPreview(
    @PreviewParameter(AboutDeveloperPreviewClass::class) value: Dev
) {
    AboutDeveloper(dev = value, linkedInOnClick = {})
}