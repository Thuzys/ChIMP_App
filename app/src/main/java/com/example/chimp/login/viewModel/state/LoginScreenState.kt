package com.example.chimp.login.viewModel.state

import com.example.chimp.login.model.LoginErrors
import com.example.chimp.model.User

/**
 * The state of the Login screen.
 */
sealed interface LoginScreenState {
    /**
     * The screen is idle.
     *
     * @property username the username
     * @property password the password
     */
    sealed class Idle(
        open val username: String,
        open val password: String,
    ) : LoginScreenState {
        abstract fun updateUsername(username: String): Idle
        abstract fun updatePassword(password: String): Idle
        val isValid: Boolean
            get() = username.isNotBlank() && password.isNotBlank()
    }

    /**
     * The screen is idle and the password is shown.
     *
     * @property username the username
     * @property password the password
     */
    data class IdleShow(
        override val username: String,
        override val password: String,
    ) : Idle(username, password) {
        override fun updateUsername(username: String): Idle = copy(username = username)
        override fun updatePassword(password: String): Idle = copy(password = password)
    }

    /**
     * The screen is idle and the password is hidden.
     *
     * @property username the username
     * @property password the password
     */
    data class IdleHide(
        override val username: String,
        override val password: String,
    ) : Idle(username, password) {
        override fun updateUsername(username: String): Idle = copy(username = username)
        override fun updatePassword(password: String): Idle = copy(password = password)
    }

    /**
     * The screen is loading.
     */
    data object Loading : LoginScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(val error: LoginErrors) : LoginScreenState

    /**
     * The screen is successful.
     *
     * @property user the user that logged in
     */
    data class Success(val user: User) : LoginScreenState
}