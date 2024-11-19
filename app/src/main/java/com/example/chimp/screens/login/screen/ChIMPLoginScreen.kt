package com.example.chimp.screens.login.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.screens.login.screen.view.ErrorView
import com.example.chimp.screens.ui.views.LoadingView
import com.example.chimp.screens.login.screen.view.LoginView
import com.example.chimp.screens.login.screen.view.RegisterView
import com.example.chimp.screens.login.viewModel.LoginViewModel
import com.example.chimp.screens.login.viewModel.state.LoginScreenState

@Composable
internal fun ChIMPLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLogin: () -> Unit = {},
) {
    when(val curr = viewModel.state.collectAsState().value) {
        is LoginScreenState.Login -> {
            LoginView(
                state = curr,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                onLoginChange = viewModel::login,
                onRegisterChange = viewModel::toRegister,
            )
        }
        is LoginScreenState.Register -> {
            RegisterView(
                state = curr,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                onRegisterChange = viewModel::register,
                onLoginChange = viewModel::toLogin,
            )
        }
        is LoginScreenState.Error -> {
            ErrorView(
                state = curr,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                tryAgain = viewModel::toLogin,
            )
        }
        is LoginScreenState.Loading -> {
            LoadingView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
        is LoginScreenState.Success -> {
            onLogin()
            viewModel.toLogin("", "")
        }
    }
}