package com.example.chimp.screens.ui.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.chimp.R

const val SOCIAL_MEDIA_MARK_TAG = "SocialMediaMark"

/**
 * The composable function that displays the social media mark.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param lightMode [Int] The light mode image resource id.
 * @param darkMode [Int] The dark mode image resource id.
 * @param contentDescription [String] The content description of the image.
 */
@Composable
fun MakeSocialMediaMark(
    modifier: Modifier = Modifier,
    lightMode: Int,
    darkMode: Int,
    contentDescription: String,
) {
    Row(
        modifier = modifier.testTag(SOCIAL_MEDIA_MARK_TAG),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MakeMark(
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp),
            lightMode = lightMode,
            darkMode = darkMode,
            contentDescription = contentDescription
        )
    }
}

private class MakeSocialMediaMarkPreviewClass : PreviewParameterProvider<Pair<Int, Int>> {
    override val values: Sequence<Pair<Int, Int>> = sequenceOf(
        Pair(R.drawable.mail_dark, R.drawable.mail_light),
        Pair(R.drawable.github_mark, R.drawable.github_mark_white),
        Pair(R.drawable.linkdin_mark, R.drawable.linkdin_mark)
    )
}

@Preview(showBackground = true)
@Composable
private fun MakeSocialMediaMarkPreview(
    @PreviewParameter(MakeSocialMediaMarkPreviewClass::class) image: Pair<Int, Int>
) {
    MakeSocialMediaMark(
        lightMode = image.first,
        darkMode = image.second,
        contentDescription = "Email Logo",
    )
}

@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MakeSocialMediaMarkPreview2(
    @PreviewParameter(MakeSocialMediaMarkPreviewClass::class) image: Pair<Int, Int>
) {
    MakeSocialMediaMark(
        lightMode = image.first,
        darkMode = image.second,
        contentDescription = "GitHub Logo",
    )
}