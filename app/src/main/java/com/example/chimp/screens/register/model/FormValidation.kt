package com.example.chimp.screens.register.model

import com.example.chimp.models.either.Either

/**
 * FormValidation is the interface that defines the service used in RegisterViewModel.
 */
interface FormValidation {
    /**
     * Validates the username.
     *
     * @param username the username to validate
     * @return an [Either] with a [String] if the username is invalid,
     * or a [Boolean] if it is valid.
     */
    suspend fun validateUsername(username: String): Either<String, Boolean>

    /**
     * Validates the password.
     *
     * @param password the password to validate
     * @return an [Either] with a [String] if the password is invalid,
     * or a [Boolean] if it is valid.
     */
    suspend fun validatePassword(password: String): Either<String, Boolean>
}