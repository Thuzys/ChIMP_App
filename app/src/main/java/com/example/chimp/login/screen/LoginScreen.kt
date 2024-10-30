package com.example.chimp.login.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chimp.login.viewModel.LoginViewModel
import com.example.chimp.login.viewModel.state.LoginScreenState
import com.example.chimp.model.User

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLogin: (User) -> Unit,
) {
    when (val state = viewModel.state) {
        is LoginScreenState.Idle -> {
            TODO()
        }
        is LoginScreenState.Loading -> {
            TODO()
        }
        is LoginScreenState.Success -> {
            onLogin(state.user)
        }
        is LoginScreenState.Error -> {
            TODO()
        }
    }
}