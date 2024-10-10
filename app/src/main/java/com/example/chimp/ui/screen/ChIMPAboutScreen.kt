package com.example.chimp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.chimp.R
import com.example.chimp.model.dev.Dev
import com.example.chimp.model.dev.Email
import com.example.chimp.model.dev.SocialMedia
import com.example.chimp.ui.view.ShowingAboutDevView
import com.example.chimp.viewModel.AboutScreenState
import com.example.chimp.viewModel.AboutViewModel
import java.net.URL

//TODO: see a more fitting file for this function
private fun getDevelopers(): List<Dev> {
    val email = Email("A50543@alunos.isel.pt")
    return listOf(
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

@Composable
fun ChIMPAboutScreen(
    modifier: Modifier = Modifier,
    viewModel: AboutViewModel
) {
    when (val state = viewModel.state) {
        is AboutScreenState.Idle -> {
            IdleAboutDevView()
        }
        is AboutScreenState.Showing -> {
            ShowingAboutDevView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                state = state,
                onLinkActivity = viewModel::linkActivity,
                onSendActivity = viewModel::sendActivity,
                onIdleChange = viewModel::idle,
                onShowDialogChange = viewModel::showDialog,
                onIsExpandedChange = viewModel::showDev
            )
        }
        is AboutScreenState.ShowDialog -> {
            ShowDialogAboutDevView()
        }
    }
}

@Composable
fun ShowDialogAboutDevView() {
    TODO("Not yet implemented")
}

@Composable
fun IdleAboutDevView() {
    TODO("Not yet implemented")
}
