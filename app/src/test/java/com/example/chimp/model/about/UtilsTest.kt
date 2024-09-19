package com.example.chimp.model.about

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.URL


class UtilsTest {

    @Test
    fun `isGitHubUrl should return true for valid GitHub URL`() {
        val url = "https://github.com/username"
        assertTrue(isGitHubUrl(URL(url)))
    }

    @Test
    fun `isGitHubUrl should return false for invalid GitHub URL`() {
        val url = "https://www.git.com/username"
        assertFalse(isGitHubUrl(URL(url)))
    }

    @Test
    fun `isLinkedInUrl should return true for valid LinkedIn URL`() {
        val url = "https://linkedin.com/in/username"
        assertTrue(isLinkedInUrl(URL(url)))
    }

    @Test
    fun `isLinkedInUrl should return false for invalid LinkedIn URL`() {
        val url = "https://www.link.com/in/username"
        assertFalse(isLinkedInUrl(URL(url)))
    }

    @Test
    fun `validateEmail should return true for valid email`() {
        val email = "test@gmail.com"
        assertTrue(validateEmail(email))
}

    @Test
    fun `validateEmail should return false for invalid email`() {
        val email = "test@gmail"
        assertFalse(validateEmail(email))
    }

    @Test
    fun `validateEmail should return false for blank email`() {
        val email = ""
        assertFalse(validateEmail(email))
    }

    @Test
    fun `validateEmail should return false for email without domain`() {
        val email = "test@"
        assertFalse(validateEmail(email))
    }

    @Test
    fun `validateEmail should return false for email without username`() {
        val email = "@gmail.com"
        assertFalse(validateEmail(email))
    }
}
