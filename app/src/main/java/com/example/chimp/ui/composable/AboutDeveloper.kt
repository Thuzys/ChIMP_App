package com.example.chimp.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.chimp.model.about.About
import com.example.chimp.model.about.Email
import com.example.chimp.model.about.SocialMedia
import java.net.URL


/**
 * The composable function that displays the developer's information.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param dev [About] The developer's information.
 * @param isExpanded Boolean The state of the developer's profile.
 * @param gitOnClick () -> Unit The function to be called when the user clicks on the GitHub icon.
 * @param linkedInOnClick () -> Unit The function to be called when the user clicks on the LinkedIn icon.
 * @param emailOnClick () -> Unit The function to be called when the user clicks on the email icon.
 * @param onShowDialogChange () -> Unit The function to be called when the user clicks on the dev's bio.
 * @param onIsExpandedChange () -> Unit The function to be called when the user clicks on the dev's profile.
 */
@Composable
fun AboutDeveloper(
    modifier: Modifier = Modifier,
    dev: About,
    isExpanded: Boolean = false,
    showDialog: Boolean = false,
    gitOnClick: () -> Unit = {},
    linkedInOnClick: () -> Unit = {},
    emailOnClick: () -> Unit = {},
    onShowDialogChange: () -> Unit = {},
    onIsExpandedChange: () -> Unit = {},
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(
            modifier = modifier
        ) {
            if (!isExpanded) {
                DeveloperHeader(
                    modifier = Modifier
                        .clickable(onClick = onIsExpandedChange),
                    profileId = dev.imageId,
                    profileName = dev.name,
                )
            }
            AnimatedVisibility(
                visible = isExpanded
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

private class AboutDeveloperPreviewClass : PreviewParameterProvider<About> {
    val email = Email("test@mail.com")
    val dev = About(
        name = "Arthur Oliveira",
        email = email,
        socialMedia = SocialMedia(
            gitHub = URL("https://test.com"),
            linkedIn = URL("https://test.com")
        )
    )
    override val values: Sequence<About> =
        sequenceOf(dev)
}


@Composable
@Preview(showBackground = true)
private fun AboutDeveloperPreview(
    @PreviewParameter(AboutDeveloperPreviewClass::class) value: About
) {
    AboutDeveloper(dev = value, linkedInOnClick = {})
}