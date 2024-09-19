package com.example.chimp.view.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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

/*TODO: put magic numbers on constants*/
@Composable
fun DeveloperHeader(
    modifier: Modifier = Modifier,
    profileId: Int,
    profileName: String,
    onClick: () -> Unit = {},
) {
    Row(
        modifier =
        modifier
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            painter = painterResource(profileId),
            contentDescription = "Developer Profile Picture",
            modifier =
                modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
        )
        Text(
            text = profileName,
            modifier =
            modifier
                .weight(1f)
                .padding(16.dp),
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
