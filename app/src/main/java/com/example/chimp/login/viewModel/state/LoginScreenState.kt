package com.example.chimp.login.viewModel.state

import com.example.chimp.model.errors.ResponseErrors
import com.example.chimp.model.users.User

/**
 * The state of the Login screen.
 */
sealed interface LoginScreenState {
    /**
     * The screen is loading.
     */
    data object Loading : LoginScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(val error: ResponseErrors) : LoginScreenState

    /**
     * The screen is successful.
     *
     * @property user the user that logged in
     */
    data class Success(val user: User) : LoginScreenState
}