package com.example.chimp.model.about

import java.net.URL

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
internal fun validateEmail(email: String): Boolean = email.matches(emailPattern)

/**
 * Validates if the URL is a GitHub URL.
 *
 * @param url the [URL] to validate.
 * @return true if the URL is a GitHub URL, false otherwise.
 */
internal fun isGitHubUrl(url: URL): Boolean {
    val gitHubRegex = Regex("^https?://(www\\.)?github\\.com/.*$")
    return gitHubRegex.matches(url.toString())
}

/**
 * Validates if the URL is a LinkedIn URL.
 *
 * @param url the [URL] to validate.
 * @return true if the URL is a LinkedIn URL, false otherwise.
 */
internal fun isLinkedInUrl(url: URL): Boolean {
    val linkedInRegex = Regex("^https?://(www\\.)?linkedin\\.com/.*$")
    return linkedInRegex.matches(url.toString())
}