package com.example.chimp.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.chimp.R
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.Email
import com.example.chimp.model.dev.SocialMedia
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
            name = "Arthur Oliveira 1",
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
            name = "Arthur Oliveira 2",
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
        )
    )
}

sealed class AboutScreenState {
    companion object {
        val devs: Set<Dev> = getDevelopers()
    }
    data object Idle : AboutScreenState()
    data class Showing(val dev: Dev) : AboutScreenState()
    data class ShowDialog(val dev: Dev) : AboutScreenState()
}

class AboutViewModel : ViewModel() {
    var state: AboutScreenState = AboutScreenState.Idle
        private set

    fun showDev(dev: Dev) {
        state = AboutScreenState.Showing(dev)
    }

    fun showDialog(dev: Dev) {
        state = AboutScreenState.ShowDialog(dev)
    }

    fun idle() {
        state = AboutScreenState.Idle
    }

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