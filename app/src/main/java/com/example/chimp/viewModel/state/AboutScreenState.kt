package com.example.chimp.viewModel.state

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.chimp.R
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.Email
import com.example.chimp.model.dev.SocialMedia
import com.example.chimp.viewModel.AboutViewModel
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

/**
 * Represents the state of the About screen.
 *
 * @see AboutViewModel
 */
sealed interface AboutScreenState {

    companion object { val devs: Set<Dev> = getDevelopers() }

    data object Idle : AboutScreenState

    data class Showing(val dev: Dev) : AboutScreenState {
        fun linkActivity(uri: Uri, context: Context) {
            val intent = Intent(Intent.ACTION_VIEW)
                .apply {
                    data = uri
                }
            context.startActivity(intent)
        }

        fun sendActivity(address: String, context: Context) {
            val intent = Intent(Intent.ACTION_SEND)
                .apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
                }
            context.startActivity(intent)
        }
    }

    data class ShowDialog(val dev: Dev) : AboutScreenState
}