package com.example.chimp.screens.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.screens.register.model.DataInput
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Loading
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.LogIn
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Register
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val DEBOUNCE_TIME = 500L

/**
 * ViewModel for the Login screen.
 *
 * This ViewModel is responsible for managing the state of the Login screen.
 *
 * @property service the service used in the ViewModel context.
 */
@OptIn(FlowPreview::class)
internal class LoginViewModel(
    private val service: RegisterService,
    private val validator: FormValidation,
    initialState: RegisterScreenState = LogIn()
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    val state: StateFlow<RegisterScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _username
                .debounce(DEBOUNCE_TIME)
                .map { username -> DataInput(username, validator.validateUsername(username)) }
                .collect { username ->
                    val currValue = state.value
                    if (currValue !is Register) return@collect
                    if (currValue.username.input !== username.input) return@collect
                    _state.emit(currValue.copy(username = username))
                }
        }
        viewModelScope.launch {
            _password
                .debounce(DEBOUNCE_TIME)
                .map { password -> DataInput(password, validator.validatePassword(password)) }
                .collect { password ->
                    val currValue = state.value
                    if (currValue !is Register) return@collect
                    if (currValue.password.input !== password.input) return@collect
                    _state.emit(currValue.copy(password = password))
                }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is LogIn) return@launch
            _state.emit(Loading)
            _state.emit(
                when (val result = service.login(username, password)) {
                    is Success -> RegisterScreenState.Success(result.value)
                    is Failure -> RegisterScreenState.Error(username, result.value)
                }
            )
        }
    }

    fun toRegister() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is LogIn) return@launch
            _username.emit(curr.username)
            _password.emit(curr.password)
            _state.emit(
                Register(
                    DataInput.fromString(curr.username),
                    DataInput.fromString(curr.password)
                )
            )
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is LogIn && curr !is Register) return@launch
            when (curr) {
                is LogIn ->
                    _state.emit(curr.copy(username = username))

                is Register -> {
                    _username.emit(username)
                    _state.emit(curr.copy(username = DataInput.fromString(username)))
                }

                else -> throw IllegalStateException("Invalid state")
            }
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is LogIn && curr !is Register) return@launch
            when (curr) {
                is LogIn ->
                    _state.emit(curr.copy(password = password))

                is Register -> {
                    _password.emit(password)
                    _state.emit(curr.copy(password = DataInput.fromString(password)))
                }

                else -> throw IllegalStateException("Invalid state")
            }
        }
    }

    fun toLogin() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Register)
                _state.emit(LogIn(curr.username.input, curr.password.input))
            else
                _state.emit(LogIn())
        }
    }

    fun toLogin(username: String, password: String) {
        viewModelScope.launch {
            _state.emit(LogIn(username, password))
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
                    is Success -> RegisterScreenState.Success(result.value)
                    is Failure -> RegisterScreenState.Error(username, result.value)
                }
            )
        }
    }
}