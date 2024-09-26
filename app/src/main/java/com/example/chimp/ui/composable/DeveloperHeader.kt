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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.chimp.R

/**
 * The size used for the developer's profile picture.
 */
private const val IMAGE_SIZE = 80

/**
 * The padding used for the developer's profile text.
 */
private const val PADDING = 16

/**
 * The composable function that displays the developer's profile picture and name.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 * @param profileId [Int] The developer's profile picture id.
 * @param profileName [String] The developer's name.
 */
@Composable
fun DeveloperHeader(
    modifier: Modifier = Modifier,
    profileId: Int?,
    profileName: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            painter = painterResource(profileId ?: R.drawable.user_mark),
            contentDescription = "Developer Profile Picture",
            modifier =
                Modifier
                    .size(IMAGE_SIZE.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
        )
        Text(
            text = profileName,
            modifier =
            Modifier
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
    )

}

@Composable
@Preview(showBackground = true)
private fun DeveloperHeaderPreview(
    @PreviewParameter(DeveloperHeaderPreviewClass::class) parameter: Pair<Int, String>
) {
    DeveloperHeader(
        profileId = parameter.first,
        profileName = parameter.second
    )
}
