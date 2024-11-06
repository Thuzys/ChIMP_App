package com.example.chimp.login.model

import com.example.chimp.model.users.User
import com.example.chimp.either.Either
import com.example.chimp.model.errors.ResponseErrors

/**
 * LoginService is the interface that defines the service used in LoginViewModel.
 */
fun interface LoginService {
    /**
     * Logs in the user with the given username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return an [Either] with the [User] if the login was successful,
     * or a [LoginErrors] if it failed.
     */
    suspend fun login(
        username: String,
        password: String
    ): Either<ResponseErrors, User>
}