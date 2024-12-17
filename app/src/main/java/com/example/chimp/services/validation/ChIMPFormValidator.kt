package com.example.chimp.services.validation

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.Either.Left
import com.example.chimp.screens.register.model.FormValidation

/**
 * The minimum length of a username.
 */
private const val MIN_USERNAME_LENGTH = 3

/**
 * The special characters that a password must contain.
 */
private val SPECIAL_CHARACTERS = Regex(".*[!@#\$%&].*")

/**
 * The uppercase letters that a username/password must contain.
 */
private val UPPER_CASE = Regex(".*[A-Z].*")

/**
 * The lowercase letters that a username/password must contain.
 */
private val LOWER_CASE = Regex(".*[a-z].*")

/**
 * The numeric characters that a password must contain.
 */
private val NUMERIC_CHARACTERS = Regex(".*[0-9].*")

/**
 * The minimum length of a password.
 */
private const val MIN_PASSWORD_LENGTH = 8

/**
 * ChIMPFormValidator is a class that validates the form fields in the ChIMP app register process.
 */
class ChIMPFormValidator : FormValidation {
    override suspend fun validateUsername(username: String): Either<String, Boolean> =
        when {
            username.isBlank() ->
                Left("Username cannot be empty.")

            username.length < MIN_USERNAME_LENGTH ->
                Left("Username must have at least 3 characters.")

            !username.matches(UPPER_CASE) ->
                Left("Username must contain at least one uppercase letter.")

            !username.matches(LOWER_CASE) ->
                Left("Username must contain at least one lowercase letter.")

            else ->
                Either.Right(true)
        }

    override suspend fun validatePassword(password: String): Either<String, Boolean> =
        when {
            password.isBlank() ->
                Left("Password cannot be empty.")

            password.length < MIN_PASSWORD_LENGTH ->
                Left("Password must have at least 3 characters.")

            !password.matches(SPECIAL_CHARACTERS) ->
                Left("Password must contain at least one special character.")

            !password.matches(UPPER_CASE) ->
                Left("Password must contain at least one uppercase letter.")

            !password.matches(NUMERIC_CHARACTERS) ->
                Left("Password must contain at least one numeric character.")

            !password.matches(LOWER_CASE) ->
                Left("Password must contain at least one lowercase letter.")

            else ->
                Either.Right(true)
        }
}