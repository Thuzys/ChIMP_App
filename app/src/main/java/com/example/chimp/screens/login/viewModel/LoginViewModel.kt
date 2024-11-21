package com.example.chimp.screens.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.screens.login.model.LoginService
import com.example.chimp.screens.login.viewModel.state.LoginScreenState
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Loading
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Login
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Register
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
    initialState : LoginScreenState = Login()
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<LoginScreenState> = _state.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Login) return@launch
            _state.emit(Loading)
            _state.emit(
                when (val result = service.login(username, password)) {
                    is Success -> LoginScreenState.Success(result.value)
                    is Failure -> LoginScreenState.Error(username, result.value)
                }
            )
        }
    }

    fun toRegister(username: String, password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Register) {
                    _state.emit(Register(username, password))
            }
        }
    }

    fun toLogin(username: String, password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Login) {
                _state.emit(Login(username, password))
            }
        }
    }

    fun register(
        username: String,
        password: String,
        invitationCode: String,
    ) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Register) return@launch
            _state.emit(Loading)
            _state.emit(
                when (
                    val result =
                        service
                            .register(username, password, invitationCode)
                ) {
                    is Success -> LoginScreenState.Success(result.value)
                    is Failure -> LoginScreenState.Error(username, result.value)
                }
            )
        }
    }
}