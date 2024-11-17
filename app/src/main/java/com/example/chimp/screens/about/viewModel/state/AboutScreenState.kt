package com.example.chimp.screens.about.viewModel.state

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.chimp.R
import com.example.chimp.screens.about.model.Dev
import com.example.chimp.screens.about.model.Email
import com.example.chimp.screens.about.model.SocialMedia
import com.example.chimp.screens.about.viewModel.AboutViewModel
import com.example.chimp.screens.chats.activity.TAG
import java.net.URL

private fun getDevelopers(): Set<Dev> {
    val email = Email("A50543@alunos.isel.pt")
    return setOf(
        Dev(
            name = "Arthur Oliveira",
            number = "50543",
            email = email,
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Thuzys"),
                linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
            ),
            bio = "I'm a student at ISEL, studying computer engineering. I'm passionate about " +
                    "technology and software development. I'm always looking for new challenges " +
                    "and opportunities to learn and grow.",
            imageId = R.drawable.thuzy_profile_pic
        ),
        Dev(
            name = "Brian Melhorado",
            number = "50471",
            email = Email("A50471@alunos.isel.pt"),
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Brgm37"),
                linkedIn = URL("https://www.linkedin.com/in/brian-melhorado-449794307/")
            ),
            imageId = R.drawable.brgm_profile_pic
        ),
        Dev(
            name = "Mariana Moraes",
            number = "50560",
            email = Email("A50560@alunos.isel.pt"),
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/mariana-moraess"),
                linkedIn = URL("https://www.linkedin.com/in/mariana-moraes-92975b330/")
            ),
            imageId = R.drawable.marys_profile_pic
        )
    )
}

private const val CHIMP_SUBJECT = "About ChIMP App"

/**
 * Represents the state of the About screen.
 *
 * @see AboutViewModel
 */
sealed interface AboutScreenState {

    companion object {
        val devs: Set<Dev> = getDevelopers()
    }

    data object Idle : AboutScreenState

    data class Showing(val dev: Dev) : AboutScreenState {
        fun linkActivity(uri: Uri, context: Context) {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                    .apply {
                        data = uri
                    }
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Failed to open URL", e)
                Toast
                    .makeText(
                        context,
                        R.string.activity_info_no_suitable_app,
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }

        fun sendActivity(address: String, context: Context) {
            try {
                val intent = Intent(Intent.ACTION_SENDTO)
                    .apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                        putExtra(Intent.EXTRA_SUBJECT, CHIMP_SUBJECT)
                    }
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "Failed to send email", e)
                Toast
                    .makeText(
                        context,
                        R.string.activity_info_no_suitable_app,
                        Toast.LENGTH_LONG
                    )
                    .show()
            }
        }
    }

    data class ShowDialog(val dev: Dev) : AboutScreenState
}