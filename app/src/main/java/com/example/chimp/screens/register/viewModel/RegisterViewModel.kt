package com.example.chimp.screens.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.repository.UserInfoRepository
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val DEBOUNCE_TIME = 500L

/**
 * ViewModel for the Login screen.
 *
 * This ViewModel is responsible for managing the state of the Register screen.
 *
 * @property service the service used in the ViewModel context.
 */
@OptIn(FlowPreview::class)
internal class RegisterViewModel(
    private val service: RegisterService,
    private val validator: FormValidation,
    private val repository: UserInfoRepository,
    initialState: RegisterScreenState = LogIn()
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    val state: StateFlow<RegisterScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val userInfo = repository.userInfo.first()
            if (userInfo != null) _state.emit(RegisterScreenState.Success)
        }

        viewModelScope.launch {
            _username
                .debounce(DEBOUNCE_TIME)
                .map { username -> DataInput(username, validator.validateUsername(username)) }
                .collect { username ->
                    val curr = state.value
                    if (curr !is Register && curr !is LogIn) return@collect
                    when (curr) {
                        is Register -> {
                            if (curr.username.input !== username.input) return@collect
                            _state.emit(curr.copy(username = username))
                        }

                        is LogIn -> {
                            if (curr.username.input !== username.input) return@collect
                            _state.emit(curr.copy(username = username))
                        }

                        else -> throw IllegalStateException("Invalid state")
                    }
                }
        }

        viewModelScope.launch {
            _password
                .debounce(DEBOUNCE_TIME)
                .map { password -> DataInput(password, validator.validatePassword(password)) }
                .collect { password ->
                    val curr = state.value
                    if (curr !is Register && curr !is LogIn) return@collect
                    when (curr) {
                        is Register -> {
                            if (curr.password.input !== password.input) return@collect
                            _state.emit(curr.copy(password = password))
                        }

                        is LogIn -> {
                            if (curr.password.input !== password.input) return@collect
                            _state.emit(curr.copy(password = password))
                        }

                        else -> throw IllegalStateException("Invalid state")
                    }
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
                    is Success -> {
                        repository.updateUserInfo(result.value)
                        RegisterScreenState.Success
                    }

                    is Failure -> RegisterScreenState.Error(username, result.value)
                }
            )
        }
    }

    fun toRegister() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Register) return@launch
            if (curr is LogIn) {
                _username.emit(curr.username.input)
                _password.emit(curr.password.input)
                _state.emit(Register(curr.username, curr.password))
            } else {
                _state.emit(Register())
            }
        }
    }

    fun updateUsername(username: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is LogIn && curr !is Register) return@launch
            _username.emit(username)
            when (curr) {
                is LogIn -> _state.emit(curr.copy(username = DataInput.fromString(username)))

                is Register -> _state.emit(curr.copy(username = DataInput.fromString(username)))

                else -> throw IllegalStateException("Invalid state")
            }
        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is LogIn && curr !is Register) return@launch
            _password.emit(password)
            when (curr) {
                is LogIn -> _state.emit(curr.copy(password = DataInput.fromString(password)))

                is Register -> _state.emit(curr.copy(password = DataInput.fromString(password)))

                else -> throw IllegalStateException("Invalid state")
            }
        }
    }

    fun toLogin() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is LogIn) return@launch
            if (curr is Register) {
                _username.emit(curr.username.input)
                _password.emit(curr.password.input)
                _state.emit(LogIn(curr.username, curr.password))
            } else {
                _state.emit(LogIn())
            }
        }
    }

    fun toLogin(username: String, password: String) {
        viewModelScope.launch {
            if (state.value is LogIn) return@launch
            _username.emit(username)
            _password.emit(password)
            _state.emit(LogIn(DataInput.fromString(username), DataInput.fromString(password)))
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
                when (val result = service.register(username, password, invitationCode)) {
                    is Success -> {
                        repository.updateUserInfo(result.value)
                        RegisterScreenState.Success
                    }

                    is Failure -> RegisterScreenState.Error(username, result.value)
                }
            )
        }
    }
}