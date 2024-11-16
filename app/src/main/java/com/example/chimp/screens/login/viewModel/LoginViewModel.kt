package com.example.chimp.screens.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.either.Either
import com.example.chimp.screens.login.model.LoginService
import com.example.chimp.screens.login.viewModel.state.Register
import com.example.chimp.screens.login.viewModel.state.Login
import com.example.chimp.screens.login.viewModel.state.LoginScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Login screen.
 *
 * This ViewModel is responsible for managing the state of the Login screen.
 *
 * @property service the service used in the ViewModel context.
 */
internal class LoginViewModel(
    private val service: LoginService,
) : ViewModel() {
    private val _state =
    MutableStateFlow<LoginScreenState>(
        Login.LoginHide("", "")
    )
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun login() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Login) return@launch
            _state.emit(LoginScreenState.Loading)
            _state.emit(
                when (val result = service.login(curr.username, curr.password)) {
                    is Success -> LoginScreenState.Success(result.value)
                    is Failure -> LoginScreenState.Error(result.value)
                }
            )
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Login && curr !is Register) return@launch
            _state.emit(
                when (curr) {
                    is Login -> curr.updateUsername(username)
                    is Register -> curr.updateUsername(username)
                    else -> curr
                }
            )
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Login && curr !is Register) return@launch
            _state.emit(
                when (curr) {
                    is Login -> curr.updatePassword(password)
                    is Register -> curr.updatePassword(password)
                    else -> curr
                }
            )
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Register) {
                _state.emit(curr.updateConfirmPassword(confirmPassword))
            }
        }
    }

    fun updateInvitationCode(invitationCode: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Register) {
                _state.emit(curr.updateInvitationCode(invitationCode))
            }
        }
    }

    fun toRegister() {
        viewModelScope.launch {
            if (state !is Register) {
                _state.emit(Register.RegisterHide("", ""))
            }
        }
    }

    fun toLogin() {
        viewModelScope.launch {
            if (state !is Login) {
                _state.emit(Login.LoginHide("", ""))
            }
        }
    }

    fun loginIsToShow() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Login ) return@launch
            _state.emit(
                when (curr) {
                    is Login.LoginShow -> curr.hidePassword()
                    is Login.LoginHide -> curr.showPassword()
                }
            )
        }
    }

    fun registerIsToShow() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Register) return@launch
            _state.emit(
                when (curr) {
                    is Register.RegisterShow -> curr.hidePassword()
                    is Register.RegisterHide -> curr.showPassword()
                }
            )
        }
    }

    fun register() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Register) return@launch
            _state.emit(LoginScreenState.Loading)
            _state.emit(
                when (
                    val result =
                        service
                            .register(curr.username, curr.password, curr.invitationCode)
                ) {
                    is Success -> LoginScreenState.Success(result.value)
                    is Failure -> LoginScreenState.Error(result.value)
                }
            )
        }
    }
}