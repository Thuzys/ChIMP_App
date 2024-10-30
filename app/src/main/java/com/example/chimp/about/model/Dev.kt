package com.example.chimp.about.model

/**
 * Represents the contact information of a developer.
 *
 * @property name The name of the dev.
 * @property number The dev's student number.
 * @property email The email of the dev.
 * @property socialMedia The social media information of the dev.
 * @property bio The biography of the dev.
 * @property imageId Resources object to query the image file from.
 */
data class Dev(
    val name: String,
    val number: String,
    val email: Email,
    val socialMedia: SocialMedia? = null,
    val bio: String? = null,
    val imageId: Int? = null,
) {
    init {
        require(name.isNotBlank()) { "Name must not be blank." }
        require(number.isNotBlank()) { "Number must not be blank." }
        bio?.let { require(it.isNotBlank()) { "Bio must not be blank." } }
    }
}