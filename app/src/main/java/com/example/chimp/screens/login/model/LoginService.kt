package com.example.chimp.screens.login.model

import com.example.chimp.models.users.User
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors

/**
 * LoginService is the interface that defines the service used in LoginViewModel.
 */
interface LoginService {
    /**
     * Logs in the user with the given username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return an [Either] with the [User] if the login was successful,
     * or a [ResponseErrors] if it failed.
     */
    suspend fun login(
        username: String,
        password: String
    ): Either<ResponseErrors, User>

    /**
     * Registers a new user with the given username, password, and invitation code.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param invitationCode the invitation code used to register
     * @return an [Either] with the [User] if the registration was successful,
     * or a [ResponseErrors] if it failed.
     */
    suspend fun register(
        username: String,
        password: String,
        invitationCode: String,
    ): Either<ResponseErrors, User>
}