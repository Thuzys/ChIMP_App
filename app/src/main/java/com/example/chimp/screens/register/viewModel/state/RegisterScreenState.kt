package com.example.chimp.screens.register.viewModel.state

import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.register.model.DataInput

/**
 * The state of the Login screen.
 */
internal sealed interface RegisterScreenState {
    /**
     * The screen is loading.
     */
    data object Loading : RegisterScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(
        val username : String,
        val error: ResponseErrors,
    ) : RegisterScreenState

    /**
     * The screen is successful.
     *
     * @property user the user that logged in
     */
    data class Success(val user: User) : RegisterScreenState

    /**
     * The screen when the user is logging in.
     */
    data class LogIn(
        val username: String = "",
        val password: String = "",
    ) : RegisterScreenState {
        val isValid: Boolean
            get() = username.isNotEmpty() && password.isNotEmpty()
    }

    /**
     * The screen when the user is registering.
     */
    data class Register(
        val username: DataInput = DataInput.initialState,
        val password: DataInput = DataInput.initialState,
    ) : RegisterScreenState
}