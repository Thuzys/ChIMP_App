package com.example.chimp.login.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.login.model.LoginService
import com.example.chimp.login.viewModel.state.Login
import com.example.chimp.login.viewModel.state.LoginScreenState
import com.example.chimp.login.viewModel.state.Register
import com.example.chimp.model.utils.Failure
import com.example.chimp.model.utils.Success
import kotlinx.coroutines.launch

/**
 * ViewModel for the Login screen.
 *
 * This ViewModel is responsible for managing the state of the Login screen.
 *
 * @property service the service used in the ViewModel context.
 */
class LoginViewModel(
    private val service: LoginService,
) : ViewModel() {
    var state: LoginScreenState by mutableStateOf(Login.LoginHide("", ""))
        private set

    fun login() {
        val curr = state
        if (curr !is Login) return
        state = LoginScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.login(curr.username, curr.password)) {
                is Success -> LoginScreenState.Success(result.value)
                is Failure -> LoginScreenState.Error(result.value)
            }
        }
    }

    fun updateUsername(username: String) {
        val curr = state
        if (curr is Login) {
            state = curr.updateUsername(username)
        }
        if (curr is Register) {
            state = curr.updateUsername(username)
        }
    }

    fun updatePassword(password: String) {
        val curr = state
        if (curr is Login) {
            state = curr.updatePassword(password)
        }
        if (curr is Register) {
            state = curr.updatePassword(password)
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        val curr = state
        if (curr is Register) {
            state = curr.updateConfirmPassword(confirmPassword)
        }
    }

    fun toRegister() {
        if (state !is Register) {
            state = Register.RegisterHide("", "")
        }
    }

    fun toLogin() {
        if (state !is Login) {
            state = Login.LoginHide("", "")
        }
    }

    fun loginIsToShow() {
        val curr = state
        if (curr !is Login) return
        state =
            when (curr) {
                is Login.LoginShow -> curr.hidePassword()
                is Login.LoginHide -> curr.showPassword()
            }
    }

    fun registerIsToShow() {
        val curr = state
        if (curr !is Register) return
        state =
            when (curr) {
                is Register.RegisterShow -> curr.hidePassword()
                is Register.RegisterHide -> curr.showPassword()
            }
    }

    fun register() {
        TODO("Not yet implemented")
    }
}