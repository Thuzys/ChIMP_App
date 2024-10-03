package com.example.chimp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.chimp.R
import com.example.chimp.model.about.About
import com.example.chimp.model.about.Email
import com.example.chimp.model.about.SocialMedia
import com.example.chimp.ui.view.AboutDevView
import com.example.chimp.viewModel.state.ChimpState
import java.net.URL

//TODO: see a more fitting file for this function
private fun getDevelopers(): List<About> {
    val email1 = Email("A50543@alunos.isel.pt")
    val email2 = Email("A50471@alunos.isel.pt")
    return listOf(
        About(
            name = "Arthur Oliveira",
            email = email1,
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Thuzys"),
                linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
            ),
            bio = "I'm a student at ISEL, studying computer engineering. I'm passionate about " +
                    "technology and software development. I'm always looking for new challenges " +
                    "and opportunities to learn and grow.",
            imageId = R.drawable.thuzy_profile_pic
        ),
        About(
            name = "Brian Melhorado",
            email = email2,
            socialMedia = SocialMedia(
                gitHub = URL("https://github.com/Brgm37"),
                linkedIn = URL("www.linkedin.com/in/brian-melhorado-449794307")
            ),
            bio = "I´m currently studying computer engineering at ISEL. ",
            imageId = R.drawable.brgm_profile_pic
        )
    )
}

//TODO: Add tag to composable functions
//TODO: add a aux repository to the project. See if is possible.
//TODO: Make tests for the functions
@Composable
fun ChIMPScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var state: ChimpState by
        remember { mutableStateOf(ChimpState.AboutDevState(getDevelopers(), context)) }
    when(val currentState = state) {
        is ChimpState.AboutDevState -> {
            AboutDevView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                state = currentState,
                onShowDialogChange = { state = currentState.toggleDialog(it) },
                onIsExpandedChange = { state = currentState.toggleExpanded(it) }
            )
        }
    }
}