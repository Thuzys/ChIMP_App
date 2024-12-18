package com.example.chimp.screens.register.model

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.User

/**
 * LoginService is the interface that defines the service used in LoginViewModel.
 */
interface RegisterService {
    /**
     * Logs in the user with the given username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return an [Either] with the [User] if the login was successful,
     * or a [ResponseError] if it failed.
     */
    suspend fun login(
        username: String,
        password: String
    ): Either<ResponseError, User>

    /**
     * Registers a new user with the given username, password, and invitation code.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param invitationCode the invitation code used to register
     * @return an [Either] with the [User] if the registration was successful,
     * or a [ResponseError] if it failed.
     */
    suspend fun register(
        username: String,
        password: String,
        invitationCode: String,
    ): Either<ResponseError, User>
}