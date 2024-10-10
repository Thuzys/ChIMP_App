package com.example.chimp.model.dev

/**
 * Represents the contact information of a developer.
 *
 * @param name The name of the dev.
 * @param email The email of the dev.
 * @param socialMedia The social media information of the dev.
 * @param bio The biography of the dev.
 * @param imageId Resources object to query the image file from.
 */
data class Dev(
    val name: String,
    val number: String,
    val email: Email,
    val socialMedia: SocialMedia? = null,
    val bio: String? = null,
    val imageId: Int? = null
) {
    init {
        require(name.isNotBlank()) { "Name must not be blank." }
        require(number.isNotBlank()) { "Number must not be blank." }
    }
}
