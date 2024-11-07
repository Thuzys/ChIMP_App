package com.example.chimp.screens.login.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.screens.ui.views.ErrorView
import com.example.chimp.screens.ui.views.LoadingView
import com.example.chimp.screens.login.screen.view.LoginView
import com.example.chimp.screens.login.screen.view.RegisterView
import com.example.chimp.screens.login.viewModel.LoginViewModel
import com.example.chimp.screens.login.viewModel.state.Login
import com.example.chimp.screens.login.viewModel.state.LoginScreenState
import com.example.chimp.screens.login.viewModel.state.Register

@Composable
fun ChIMPLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLogin: () -> Unit,
) {
    when(val curr = viewModel.state.collectAsState().value) {
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
                onInvitationCodeChange = viewModel::updateInvitationCode,
                onRegisterChange = viewModel::register,
                onLoginChange = viewModel::toLogin,
                isToShowChange = viewModel::registerIsToShow,
            )
        }
        is LoginScreenState.Error -> {
            ErrorView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                error = curr.error,
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
        }
    }
}