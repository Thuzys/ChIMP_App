package com.example.chimp.model.about

import org.junit.Test
import java.net.URL

class AboutTest {

    @Test
    fun `successful About instantiation`() {
        val name = "Test Test"
        val email = "test@gmail.com"
        val gitHubUrl = "https://github.com/username"
        val linkedInUrl = "https://linkedin.com/in/username"
        val socialMedia = SocialMedia(URL(gitHubUrl), URL(linkedInUrl))
        val bio = "Test bio"
        About(name, Email(email), socialMedia, bio)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `unsuccessful About instantiation due to blank name`() {
        val name = ""
        val email = "test@gmail.com"
        About(name, Email(email))
    }
}