

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.model.about.About
import com.example.chimp.model.about.Email
import com.example.chimp.model.about.SocialMedia
import com.example.chimp.ui.composable.MakeSocialMediaMark
import com.example.chimp.view.ShowDialog
import java.net.URL

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
 * @param dev [About] The developer's information.
 * @param uriOnClick (Uri) -> Unit The function to be called when the user clicks on the GitHub/LinkedIn icon.
 * @param emailOnClick (String) -> Unit The function to be called when the user clicks on the email icon.
 * @param onClick () -> Unit The function to be called when the user clicks on the developer's profile.
 */
@Composable
fun DeveloperContent(
    modifier: Modifier = Modifier,
    dev: About,
    uriOnClick: (Uri) -> Unit = {},
    emailOnClick: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = dev.imageId ?: R.drawable.user_mark),
                contentDescription = "Developer Profile Picture",
                modifier =
                modifier
                    .size(IMAGE_SIZE.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
            )
        }
        Text(
            text = dev.name,
            modifier =
            modifier
                .fillMaxWidth()
                .padding(PADDING.dp),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(PADDING.dp)
                .clickable { showDialog = !showDialog },
        ) {
            Text(
                text = dev.bio ?: "No biography available.",
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINES
            )
        }
        HorizontalDivider()
        SocialMediaLayout(dev.socialMedia, modifier, uriOnClick)
        MakeSocialMediaMark(
            modifier = modifier,
            lightMode = R.drawable.mail_dark,
            darkMode = R.drawable.mail_light,
            contendDescription = "Email Logo",
            contend = dev.email.email,
            onClick = emailOnClick
        )
        ShowDialog(
            showDialog = showDialog,
            onDismissRequest = { showDialog = false }
        ) {
            Text(
                text = dev.bio ?: "No biography available.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

/**
 * The composable function that displays the developer's social media icons.
 * @param social [About] The developer's information.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param uriOnClick (Uri) -> Unit The function to be called when the user clicks on the GitHub/LinkedIn icon.
 */
@Composable
private fun SocialMediaLayout(
    social: SocialMedia?,
    modifier: Modifier,
    uriOnClick: (Uri) -> Unit
) {
    social?.let { socialMedia ->
        socialMedia.gitHub?.let { gitHub ->
            MakeSocialMediaMark(
                modifier = modifier,
                lightMode = R.drawable.github_mark,
                darkMode = R.drawable.github_mark_white,
                contendDescription = "GitHub Logo",
                contend = Uri.parse(gitHub.toString()),
                onClick = uriOnClick
            )
            HorizontalDivider()
        }
        socialMedia.linkedIn?.let { linkedIn ->
            MakeSocialMediaMark(
                modifier = modifier,
                lightMode = R.drawable.linkdin_mark,
                darkMode = R.drawable.linkdin_mark,
                contendDescription = "LinkedIn Logo",
                contend = Uri.parse(linkedIn.toString()),
                onClick = uriOnClick
            )
            HorizontalDivider()
        }
    }
}

private class DeveloperContentPreviewClass : PreviewParameterProvider<About> {
    override val values: Sequence<About> = sequenceOf(
        About(
            name = "Arthur Oliveira",
            email = Email("A50543@alunos.isel.pt"),
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Thuzys"),
                linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
            ),
            imageId = R.drawable.thuzy_profile_pic
        )
    )
}


@Composable
@Preview(
    showBackground = true,
)
private fun DeveloperContendPreview(
    @PreviewParameter(DeveloperContentPreviewClass::class) devInfo: About
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
    @PreviewParameter(DeveloperContentPreviewClass::class) devInfo: About
) {
    DeveloperContent(
        dev = devInfo
    )
}