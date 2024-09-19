package com.example.chimp.model.about

/**
 * Represents an Email.
 *
 * @param email the [String] containing the email.
 * @throws IllegalArgumentException if the given email is not valid.
 */
data class Email(val email: String) {
    init {
        require(validateEmail(email)) { "Invalid email pattern." }
    }

    override fun toString(): String = email
}