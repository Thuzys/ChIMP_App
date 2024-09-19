package com.example.chimp.view.about

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.chimp.R

/*TODO: Pass the remaining parameter to the function*/
@Composable
fun AboutDeveloper(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onClick: () -> Unit = {},
) {
    Card(
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(
            modifier =
            modifier
                .fillMaxWidth()
        ) {
            //TODO: Change parameter input to be dynamic (remove this value)
            val context = LocalContext.current
            val gitSiteFunc: (String) -> Unit = {
                val intent = Intent(Intent.ACTION_VIEW)
                    .apply {
                        data = android.net.Uri.parse(it)
                    }
                context.startActivity(intent)
            }
            val emailSiteFunc: (String) -> Unit = {
                val intent = Intent(Intent.ACTION_SEND)
                    .apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(it))
                    }
                context.startActivity(intent)
            }
            if (!isExpanded) {
                //TODO: Change parameter input to be dynamic
                DeveloperHeader(modifier, R.drawable.thuzy_profile_pic, "Thuzy", onClick)
            }
            AnimatedVisibility(
                visible = isExpanded
            ) {
                DeveloperContent(
                    modifier,
                    R.drawable.thuzy_profile_pic,
                    "Thuzy"
                    , "https://github.com/Thuzys",
                    "arthurcnoliveira@gmail.com",
                    gitOnClick = gitSiteFunc,
                    emailOnClick = emailSiteFunc,
                    onClick = onClick
                )
            }
        }
    }
}

private class AboutDeveloperPreviewClass : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(false, true)
}


@Composable
@Preview(showBackground = true)
private fun AboutDeveloperPreview(
    @PreviewParameter(AboutDeveloperPreviewClass::class) isExpanded: Boolean
) {
    AboutDeveloper(isExpanded = isExpanded)
}