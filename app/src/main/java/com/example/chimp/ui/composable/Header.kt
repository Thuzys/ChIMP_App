package com.example.chimp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.chimp.R

const val HEADER_TAG = "Header"
const val IMAGE_HEADER_TAG = "ImageHeader"
const val TEXT_HEADER_TAG = "TextHeader"

/**
 * The size used for the developer's profile picture.
 */
private const val IMAGE_SIZE = 80

/**
 * The padding used for the developer's profile text.
 */
private const val PADDING = 16

/**
 * The composable function that displays the user's profile picture and name.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param profileId [Int] The developer's profile picture id.
 * @param profileName [String] The developer's name.
 */
@Composable
fun Header(
    modifier: Modifier = Modifier,
    profileId: Int? = null,
    profileName: String,
) {
    Row(
        modifier = modifier.testTag(HEADER_TAG),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            painter = painterResource(profileId ?: R.drawable.user_mark),
            contentDescription = "Developer Profile Picture",
            modifier =
                Modifier
                    .testTag(IMAGE_HEADER_TAG)
                    .size(IMAGE_SIZE.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
        )
        Text(
            text = profileName,
            modifier =
            Modifier
                .testTag(TEXT_HEADER_TAG)
                .weight(1f)
                .padding(PADDING.dp),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )
    }
}

private class DeveloperHeaderPreviewClass : PreviewParameterProvider<Pair<Int, String>> {
    override val values: Sequence<Pair<Int, String>> = sequenceOf(
        Pair(R.drawable.thuzy_profile_pic, "Thuzy"),
        Pair(R.drawable.user_mark, "User")
    )
}

@Composable
@Preview(showBackground = true)
private fun DeveloperHeaderPreview(
    @PreviewParameter(DeveloperHeaderPreviewClass::class) parameter: Pair<Int, String>
) {
    Header(
        profileId = parameter.first,
        profileName = parameter.second
    )
}
