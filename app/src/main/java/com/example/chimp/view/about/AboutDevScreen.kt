package com.example.chimp.view.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chimp.R
import com.example.chimp.model.about.About
import com.example.chimp.model.about.Email
import com.example.chimp.model.about.SocialMedia
import java.net.URL

/**
 * The developers will be hardcoded for now, but in the future, will encapsulate on viewmodel class
 */
fun getDevelopers(): List<About> {
    val email = Email("A50543@alunos.isel.pt")
    return listOf(
        About(
            name = "Arthur Oliveira",
            email = email,
            socialMedia = SocialMedia(
                email = email,
                gitHub = URL("https://github.com/Thuzys"),
                linkedIn = URL("https://www.linkedin.com/in/arthur-cesar-oliveira-681643184/")
            )
        )
    )
}

@Composable
fun AboutDevScreen(modifier: Modifier = Modifier) {
    val developers = getDevelopers().associateWith { R.drawable.thuzy_profile_pic }
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        var isExpanded by remember { mutableStateOf(false) }
        /*TODO: pass the others devs (maybe a list)*/
        AboutDeveloper(isExpanded = isExpanded, dev = developers.keys.first()){
            isExpanded = !isExpanded
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutDevScreen()
}