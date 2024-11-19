package com.example.chimp.screens.login.viewModel.state

import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User

/**
 * Represents the state of the Login screen.
 *
 * The username must have:
 * - At least 3 characters
 * - Only alphanumeric characters
 */
private val userRegex = Regex("^[a-zA-Z0-9]{3,}\$")

/**
 * Represents the state of the Login screen.
 *
 * The password must have:
 * - At least 1 uppercase letter
 * - At least 1 digit
 * - At least 1 special character
 * - At least 8 characters
 */
private val passwordRegex = Regex(
    "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}\$"
)

/**
 * The state of the Login screen.
 */
internal sealed interface LoginScreenState {
    /**
     * The screen is loading.
     */
    data object Loading : LoginScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(
        val username : String,
        val error: ResponseErrors,
    ) : LoginScreenState

    /**
     * The screen is successful.
     *
     * @property user the user that logged in
     */
    data class Success(val user: User) : LoginScreenState

    /**
     * The screen when the user is logging in.
     */
    data class Login(
        val username: String = "",
        val password: String = "",
    ) : LoginScreenState {
        companion object {
            fun isValid(
                username: String,
                password: String,
            ): Boolean = userRegex matches username && passwordRegex matches password
        }
    }

    /**
     * The screen when the user is registering.
     */
    data class Register(
        val username: String = "",
        val password: String = "",
    ) : LoginScreenState {
        companion object {
            fun isValid(
                username: String,
                password: String,
                confirmPassword: String,
            ): Boolean =
                userRegex matches username
                && passwordRegex matches password
                && password == confirmPassword
        }
    }
}