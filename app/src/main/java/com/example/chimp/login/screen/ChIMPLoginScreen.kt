package com.example.chimp.login.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.login.screen.view.LoginView
import com.example.chimp.login.screen.view.RegisterView
import com.example.chimp.login.viewModel.LoginViewModel
import com.example.chimp.login.viewModel.state.Login
import com.example.chimp.login.viewModel.state.LoginScreenState
import com.example.chimp.login.viewModel.state.Register

@Composable
fun ChIMPLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
) {
    when(val curr = viewModel.state) {
        is Login -> {
            LoginView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                vm = curr,
                onUsernameChange = viewModel::updateUsername,
                onPasswordChange = viewModel::updatePassword,
                isToShowChange = viewModel::loginIsToShow,
                onLoginChange = viewModel::login,
                onRegisterChange = viewModel::toRegister,
            )
        }
        is Register -> {
            RegisterView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                vm = curr,
                onUsernameChange = viewModel::updateUsername,
                onPasswordChange = viewModel::updatePassword,
                onConfirmPasswordChange = viewModel::updateConfirmPassword,
                onRegisterChange = viewModel::register,
                onLoginChange = viewModel::toLogin,
                isToShowChange = viewModel::registerIsToShow,
            )
        }
        is LoginScreenState.Error -> {
            TODO()
        }
        is LoginScreenState.Loading -> {
            TODO()
        }
        is LoginScreenState.Success -> {
            TODO()
        }
    }
}