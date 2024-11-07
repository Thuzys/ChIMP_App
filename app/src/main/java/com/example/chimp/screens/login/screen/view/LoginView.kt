package com.example.chimp.screens.login.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.login.screen.composable.BaseView
import com.example.chimp.screens.login.screen.composable.MakePasswordTextField
import com.example.chimp.screens.login.screen.composable.MakeUsernameTextField
import com.example.chimp.screens.login.viewModel.state.Login
import com.example.chimp.screens.login.viewModel.state.Visibility
import com.example.chimp.ui.composable.MySpacer
import com.example.chimp.ui.composable.MakeButton

/**
 * The tag used to identify the login view in automated tests.
 */
const val LOGIN_VIEW_TEST_TAG = "LoginViewTestTag"

/**
 * The horizontal padding of the text field.
 */
private const val HORIZONTAL_PADDING = 10

/**
 * The LoginView composable that displays the login screen.
 *
 * @param modifier the modifier to be applied to the composable
 * @param vm the view model that holds the state of the login screen
 * @param onUsernameChange the callback to be invoked when the username changes
 * @param onPasswordChange the callback to be invoked when the password changes
 * @param isToShowChange the callback to be invoked when the user wants to show/hide the password
 * @param onLoginChange the callback to be invoked when the user wants to login
 * @param onRegisterChange the callback to be invoked when the user wants to register
 */
@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    vm: Login,
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    isToShowChange: () -> Unit = {},
    onLoginChange: () -> Unit = {},
    onRegisterChange: () -> Unit = {},
) {
    BaseView(
        modifier = modifier.testTag(LOGIN_VIEW_TEST_TAG),
        visibility = vm as Visibility
    ) { isToShow, imeVisible ->
        MySpacer()
        Text(
            text = stringResource(R.string.login_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        MySpacer()
        MakeUsernameTextField(
            value = vm.username,
            onUsernameChange = onUsernameChange
        )
        MySpacer()
        MakePasswordTextField(
            value = vm.password,
            label = stringResource(R.string.password),
            onPasswordChange = onPasswordChange,
            isToShow = isToShow,
            isToShowChange = isToShowChange
        )
        val animatedButtonsVisibility by animateFloatAsState(
            targetValue = if (imeVisible) 0f else 1f,
            label = "Buttons Visibility"
        )
        AnimatedVisibility(
            visible = !imeVisible
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(animatedButtonsVisibility),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MakeButton(
                    modifier = Modifier
                        .padding(HORIZONTAL_PADDING.dp)
                        .fillMaxWidth(0.5f),
                    text = stringResource(R.string.login),
                    enable = vm.isValid,
                    onClick = onLoginChange
                )
                MakeButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.register),
                    onClick = onRegisterChange
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun PreviewLoginView() {
    var vm: Login by remember {
        mutableStateOf(Login.LoginHide("dummy", "dummy"))
    }
    LoginView(
        vm = vm,
        onUsernameChange = { vm = vm.updateUsername(it) },
        onPasswordChange = { vm = vm.updatePassword(it) },
        isToShowChange = {
            vm =
                when (val curr = vm) {
                    is Login.LoginShow -> curr.hidePassword()
                    is Login.LoginHide -> curr.showPassword()
                }
        },
    )
}