package com.example.chimp.about.model

import org.junit.Test
import java.net.URL


class SocialMediaTest {
    @Test
    fun gitHubUrlIsValid() {
        SocialMedia(gitHub = URL("https://github.com/test"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun gitHubUrlIsInvalid() {
        SocialMedia(gitHub = URL("https://gitlab.com/test"))
    }

    @Test
    fun linkedInUrlIsValid() {
        SocialMedia(linkedIn = URL("https://linkedin.com/test"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun linkedInUrlIsInvalid() {
        SocialMedia(linkedIn = URL("https://facebook.com/test"))
    }
}