package com.example.chimp.model.about

import java.net.URL

/**
 * Data class that represents the contact information of the user.
 *
 * @param email The email of the user.
 * @param gitHub The GitHub URL of the user.
 * @param linkedIn The LinkedIn URL of the user.

 */
data class SocialMedia(
    val email: Email,
    val gitHub: URL? = null,
    val linkedIn: URL? = null,
) {
    init {
        gitHub?.let { require(isGitHubUrl(it)) { "Invalid GitHub URL." } }
        linkedIn?.let { require(isLinkedInUrl(it)) { "Invalid LinkedIn URL." } }
    }

}
