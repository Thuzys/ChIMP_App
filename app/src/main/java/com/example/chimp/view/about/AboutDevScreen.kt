package com.example.chimp.view.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.chimp.R
import com.example.chimp.model.about.About
import com.example.chimp.model.about.Email
import com.example.chimp.model.about.SocialMedia
import java.net.URL

// TODO: Make one screen with state for the application.

 //TODO: The developers will be hardcoded for now, but in production code,
 // will encapsulate on viewmodel class
private fun getDevelopers(): List<About> {
    val email = Email("A50543@alunos.isel.pt")
    return listOf(
        About(
            name = "Arthur Oliveira",
            email = email,
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Thuzys"),
                linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
            ),
            bio = "I'm a student at ISEL, studying computer engineering. I'm passionate about " +
                    "technology and software development. I'm always looking for new challenges " +
                    "and opportunities to learn and grow.",
            imageId = R.drawable.thuzy_profile_pic
        )
    )
}


 // TODO: The gitFunc and emailFunc are private global functions for now,
 //  but in production code, will encapsulate on viewmodel class
private fun uriFunc(uri: Uri, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
        .apply {
            data = uri
        }
    context.startActivity(intent)
}

private fun emailFunc(email: String, context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
        .apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
    context.startActivity(intent)
}

/**
 * The composable function that displays the developer's information.
 * @param modifier [Modifier] The modifier to be applied to the layout.
 */
@Composable
fun AboutDevScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        val context = LocalContext.current
        val developers = getDevelopers()
        for (dev in developers) {
            AboutDeveloper(
                dev = dev,
                uriOnClick = { uriFunc(it, context) },
                emailOnClick = { emailFunc(it, context) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AboutScreenPreview() {
    AboutDevScreen()
}