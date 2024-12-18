package com.example.chimp.screens.register.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.screens.register.screen.view.ErrorView
import com.example.chimp.screens.ui.views.LoadingView
import com.example.chimp.screens.register.screen.view.LoginView
import com.example.chimp.screens.register.screen.view.RegisterView
import com.example.chimp.screens.register.viewModel.RegisterViewModel
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState

const val LOGIN_SCREEN_TAG = "LoginScreen"

@Composable
internal fun ChIMPLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel,
    onLogin: () -> Unit = {},
) {
    val curr = viewModel.state.collectAsState().value
    Log.i(LOGIN_SCREEN_TAG, "State: ${curr::class.simpleName}")
    when(curr) {
        is RegisterScreenState.LogIn -> {
            LoginView(
                state = curr,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                onLogin = viewModel::login,
                onRegisterClick = viewModel::toRegister,
                onPasswordChange = viewModel::updatePassword,
                onUsernameChange = viewModel::updateUsername,
            )
        }
        is RegisterScreenState.Register -> {
            RegisterView(
                state = curr,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                onRegister = viewModel::register,
                onLoginClick = viewModel::toLogin,
                onUsernameChange = viewModel::updateUsername,
                onPasswordChange = viewModel::updatePassword,
            )
        }
        is RegisterScreenState.Error -> {
            ErrorView(
                state = curr,
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                tryAgain = viewModel::toLogin,
            )
        }
        is RegisterScreenState.Loading -> {
            LoadingView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
        is RegisterScreenState.Success -> {
            onLogin()
            viewModel.toLogin()
        }
    }
}