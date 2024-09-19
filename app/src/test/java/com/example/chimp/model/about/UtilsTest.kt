package com.example.chimp.model.about

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.URL


class UtilsTest {

    @Test
    fun `isGitHubUrl should return true for valid GitHub URL`() {
        val url = "https://github.com/Thuzys"
        assertTrue(isGitHubUrl(URL(url)))
    }

    @Test
    fun `isGitHubUrl should return false for invalid GitHub URL`() {
        val url = "https://www.git.com/Thuzys"
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

}