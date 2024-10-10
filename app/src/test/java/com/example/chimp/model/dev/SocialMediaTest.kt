package com.example.chimp.model.dev

import org.junit.Test
import java.net.URL

class SocialMediaTest {

    @Test
    fun `successful SocialMedia instantiation`() {
        val gitHubUrl = "https://github.com/username"
        val linkedInUrl = "https://linkedin.com/in/username"
        SocialMedia(URL(gitHubUrl), URL(linkedInUrl))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `unsuccessful SocialMedia instantiation due to invalid parameters`() {
        val gitHubUrl = "https://www.git.com/username"
        val linkedInUrl = "https://linkedin.com/in/username"
        SocialMedia(URL(gitHubUrl), URL(linkedInUrl))
    }
}