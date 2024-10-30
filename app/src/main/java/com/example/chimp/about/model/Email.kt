package com.example.chimp.about.model

/**
 * Represents an Email.
 *
 * @param email the [String] containing the email.
 * @throws IllegalArgumentException if the given email is not valid.
 */
@JvmInline
value class Email(val email: String) {
    init {
        require(validateEmail(email)) { "Invalid email pattern." }
    }
    override fun toString(): String = email
}

/**
 * Represents a valid email address pattern.
 */
private val emailPattern: Regex = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex()

/**
 * Validates if an email has the correct pattern.
 *
 * An email is considered to have the correct pattern if it follows the standard format:
 * - Starts with one or more letters (uppercase or lowercase).
 * - Contains an '@' symbol.
 * - Followed by one or more characters.
 * - Contains a dot ('.') symbol.
 * - Ends with one or more characters.
 *
 * @param email The email to be validated.
 * @return true if the email has the correct pattern, false otherwise.
 */
private fun validateEmail(email: String): Boolean = email.matches(emailPattern)