package com.example.chimp.model.about

import java.net.URL

/**
 * Data class that represents the contact information of the user.
 *
 * @param email The email of the user.
 * @param gitHub The GitHub URL of the user.
 * @param linkedIn The LinkedIn URL of the user.

 */
data class Contact(
    val email: Email,
    val gitHub: URL? = null,
    val linkedIn: URL? = null,
) {
    init {
        gitHub?.let { require(isGitHubUrl(it)) { "Invalid GitHub URL." } }
        linkedIn?.let { require(isLinkedInUrl(it)) { "Invalid LinkedIn URL." } }
    }

}

/**
 * Validates if the URL is a GitHub URL.
 *
 * @param url the [URL] to validate.
 * @return true if the URL is a GitHub URL, false otherwise.
 */
private fun isGitHubUrl(url: URL): Boolean {
    val gitHubRegex = Regex("^https?://(www\\.)?github\\.com/.*$")
    return gitHubRegex.matches(url.toString())
}

/**
 * Validates if the URL is a LinkedIn URL.
 *
 * @param url the [URL] to validate.
 * @return true if the URL is a LinkedIn URL, false otherwise.
 */
private fun isLinkedInUrl(url: URL): Boolean {
    val linkedInRegex = Regex("^https?://(www\\.)?linkedin\\.com/.*$")
    return linkedInRegex.matches(url.toString())
}
