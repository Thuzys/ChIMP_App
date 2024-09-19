package com.example.chimp.model.about

/**
 * Represents the contact information of the user.
 *
 * @param name The name of the user.
 * @param email The email of the user.
 * @param socialMedia The social media information of the user.
 * @param bio The biography of the user.
 */
data class About(
    val name: String,
    val email: Email,
    val socialMedia: SocialMedia? = null,
    val bio: String? = null,
) {
    init {
        require(name.isNotBlank()) { "Name must not be blank." }
    }

}
