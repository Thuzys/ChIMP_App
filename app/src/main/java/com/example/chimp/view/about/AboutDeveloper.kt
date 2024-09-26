package com.example.chimp.view.about

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
 * @param uriOnClick (Uri) -> Unit The function to be called when the user clicks on the GitHub icon.
 * @param emailOnClick (String) -> Unit The function to be called when the user clicks on the email icon.
 */
@Composable
fun AboutDeveloper(
    modifier: Modifier = Modifier,
    dev: About,
    uriOnClick: (Uri) -> Unit = {},
    emailOnClick: (String) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(
            modifier =
            modifier
                .fillMaxWidth()
        ) {
            if (!isExpanded) {
                DeveloperHeader(modifier, dev.imageId, dev.name) {
                    isExpanded = !isExpanded
                }
            }
            AnimatedVisibility(
                visible = isExpanded
            ) {
                DeveloperContent(
                    modifier,
                    dev,
                    uriOnClick = uriOnClick,
                    emailOnClick = emailOnClick,
                ) {
                    isExpanded = !isExpanded
                }
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
    AboutDeveloper(dev = value)
}