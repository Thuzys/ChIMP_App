package com.example.chimp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.Email
import com.example.chimp.model.dev.SocialMedia
import java.net.URL

const val DEVELOPER_CONTENT_TAG = "DeveloperContent"
const val DEVELOPER_CONTENT_CONTAINER_TAG = "DeveloperContentContainer"
const val DEVELOPER_CONTENT_IMAGE_TAG = "DeveloperContentImage"
const val DEVELOPER_CONTENT_NAME_TAG = "DeveloperContentName"
const val DEVELOPER_CONTENT_BIO_TAG = "DeveloperContentBio"
const val DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG = "DeveloperContentSocialMedia"
const val DEVELOPER_CONTENT_EMAIL_TAG = "DeveloperContentEmail"
const val DEVELOPER_CONTENT_COMPLETE_BIO_TAG = "DeveloperContentCompleteBio"

/**
 * The size used for the developer's profile picture.
 */
private const val IMAGE_SIZE = 200

/**
 * The padding used for the developer's profile text.
 */
private const val PADDING = 16

/**
 * The maximum number of lines for the biography.
 */
private const val MAX_LINES = 2


/**
 * The composable function that displays the developer's information.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param dev [Dev] The developer's information.
 * @param gitOnClick (Uri) -> Unit The function to be called when the user clicks on the GitHub/LinkedIn icon.
 * @param emailOnClick (String) -> Unit The function to be called when the user clicks on the email icon.
 * @param onShowDialog () -> Unit The function to be called when the user clicks on the developer's profile.
 */
@Composable
fun DeveloperContent(
    modifier: Modifier = Modifier,
    dev: Dev,
    showDialog: Boolean = false,
    gitOnClick: () -> Unit = {},
    linkedInOnClick: () -> Unit = {},
    emailOnClick: () -> Unit = {},
    onShowDialog: () -> Unit = {},
    onIsExpanded: () -> Unit = {},
) {
    Column(
        modifier = modifier.testTag(DEVELOPER_CONTENT_TAG),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .testTag(DEVELOPER_CONTENT_CONTAINER_TAG)
                .clickable(onClick = onIsExpanded),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = dev.imageId ?: R.drawable.user_mark),
                contentDescription = "Developer Profile Picture",
                modifier = Modifier
                    .size(IMAGE_SIZE.dp)
                    .testTag(DEVELOPER_CONTENT_IMAGE_TAG)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
            )
            Text(
                text = "${dev.name} - ${dev.number}",
                modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag(DEVELOPER_CONTENT_NAME_TAG)
                    .padding(PADDING.dp),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(PADDING.dp)
                .testTag(DEVELOPER_CONTENT_BIO_TAG)
                .clickable(onClick = onShowDialog),
            text = dev.bio ?: "No biography available.",
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = MAX_LINES
        )
        HorizontalDivider()
        dev.socialMedia?.let { social ->
            SocialMediaLayout(
                modifier = Modifier
                    .testTag(DEVELOPER_CONTENT_SOCIAL_MEDIA_TAG)
                    .fillMaxWidth(),
                social = social,
                gitOnClick = gitOnClick,
                linkedInOnClick = linkedInOnClick,
            )
        }
        MakeSocialMediaMark(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(DEVELOPER_CONTENT_EMAIL_TAG)
                .clickable(onClick = emailOnClick),
            lightMode = R.drawable.mail_dark,
            darkMode = R.drawable.mail_light,
            contentDescription = "Email Logo",
        )
        ShowDialog(
            showDialog = showDialog,
            onDismissRequest = onShowDialog
        ) {
            Text(
                modifier = Modifier.testTag(DEVELOPER_CONTENT_COMPLETE_BIO_TAG),
                text = dev.bio ?: "No biography available.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

private class DeveloperContentPreviewClass : PreviewParameterProvider<Dev> {
    override val values: Sequence<Dev> = sequenceOf(
        Dev(
            name = "Arthur Oliveira",
            number = "50543",
            email = Email("A50543@alunos.isel.pt"),
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Thuzys"),
                linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
            ),
            imageId = R.drawable.thuzy_profile_pic
        ),
    )
}


@Composable
@Preview(
    showBackground = true,
)
private fun DeveloperContendPreview(
    @PreviewParameter(DeveloperContentPreviewClass::class) devInfo: Dev
) {
    DeveloperContent(
        dev = devInfo
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF808080,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DeveloperContendPreviewDark(
    @PreviewParameter(DeveloperContentPreviewClass::class) devInfo: Dev
) {
    DeveloperContent(
        dev = devInfo
    )
}