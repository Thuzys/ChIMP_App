package com.example.chimp.login.viewModel.state

import com.example.chimp.login.model.LoginErrors
import com.example.chimp.model.User

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
    data class Error(val error: LoginErrors) : LoginScreenState

    /**
     * The screen is successful.
     *
     * @property user the user that logged in
     */
    data class Success(val user: User) : LoginScreenState
}