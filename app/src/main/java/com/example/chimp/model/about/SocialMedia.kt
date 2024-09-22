package com.example.chimp.model.about

import java.net.URL

/**
 * Data class that represents the social information of the user.
 *
 * @param gitHub The GitHub URL of the user.
 * @param linkedIn The LinkedIn URL of the user.

 */
//TODO: Remove the email from the social media data class
data class SocialMedia(
    val gitHub: URL? = null,
    val linkedIn: URL? = null,
) {
    init {
        gitHub?.let { require(isGitHubUrl(it)) { "Invalid GitHub URL." } }
        linkedIn?.let { require(isLinkedInUrl(it)) { "Invalid LinkedIn URL." } }
    }

}
