package com.example.chimp.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.chimp.R
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.SocialMedia

const val SOCIAL_MEDIA_LAYOUT_TAG = "SocialMediaLayout"

/**
 * The composable function that displays the developer's social media icons.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param social [Dev] The developer's information.
 * @param gitOnClick () -> Unit The function to be called when the user clicks on the GitHub icon.
 * @param linkedInOnClick () -> Unit The function to be called when the user clicks on the LinkedIn icon.
 */
@Composable
fun SocialMediaLayout(
    modifier: Modifier = Modifier,
    social: SocialMedia,
    gitOnClick: () -> Unit,
    linkedInOnClick: () -> Unit,
) {
    social.gitHub?.let {
        MakeSocialMediaMark(
            modifier = modifier
                .clickable(onClick = gitOnClick)
                .testTag(SOCIAL_MEDIA_LAYOUT_TAG),
            lightMode = R.drawable.github_mark,
            darkMode = R.drawable.github_mark_white,
            contentDescription = "GitHub Logo",
        )
        HorizontalDivider()
    }
    social.linkedIn?.let {
        MakeSocialMediaMark(
            modifier = modifier
                .clickable(onClick = linkedInOnClick)
                .testTag(SOCIAL_MEDIA_LAYOUT_TAG),
            lightMode = R.drawable.linkdin_mark,
            darkMode = R.drawable.linkdin_mark,
            contentDescription = "LinkedIn Logo",
        )
        HorizontalDivider()
    }
}