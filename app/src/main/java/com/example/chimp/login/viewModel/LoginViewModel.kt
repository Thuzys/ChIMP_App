package com.example.chimp.login.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.login.model.LoginService
import com.example.chimp.login.viewModel.state.LoginScreenState
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
): ViewModel() {
    var state: LoginScreenState by mutableStateOf(
        LoginScreenState.IdleHide("", "")
    )
        private set
    fun login(username: String, password: String) {
        if (state != LoginScreenState.Loading) {
            state = LoginScreenState.Loading
            viewModelScope.launch {
                state =
                    when (val result = service.login(username, password)) {
                        is Success -> LoginScreenState.Success(result.value)
                        is Failure -> LoginScreenState.Error(result.value)
                    }
            }
        }
    }
}