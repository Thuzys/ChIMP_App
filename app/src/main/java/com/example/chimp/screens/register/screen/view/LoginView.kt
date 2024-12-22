package com.example.chimp.screens.register.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.chimp.screens.register.screen.composable.BaseView
import com.example.chimp.screens.register.screen.composable.MakePasswordTextField
import com.example.chimp.screens.register.screen.composable.MakeUsernameTextField
import com.example.chimp.screens.register.screen.composable.ShowErrorMessage
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState
import com.example.chimp.screens.ui.composable.MySpacer
import com.example.chimp.screens.ui.composable.MakeButton

/**
 * The tag used to identify the login view in automated tests.
 */
const val LOGIN_VIEW_TEST_TAG = "LoginViewTestTag"

/**
 * The tag used to identify the login view username input text field.
 */
const val USERNAME_TEXT_FIELD = "UsernameTextField"

/**
 * The horizontal padding of the text field.
 */
private const val HORIZONTAL_PADDING = 10

/**
 * The width of the button.
 */
private const val BUTTON_WIDTH = 150

/**
 * The tag used to identify the login view login button.
 */
const val LOGIN_VIEW_LOGIN_BUTTON = "LoginLoginButton"

/**
 * The tag used to identify the login view register button.
 */
const val LOGIN_VIEW_REGISTER_BUTTON = "LoginViewRegisterButton"

/**
 * The LoginView composable that displays the login screen.
 *
 * @param modifier the modifier to be applied to the composable
 * @param onLogin the callback to be invoked when the user wants to login
 * @param onRegisterClick the callback to be invoked when the user wants to register
 */
@Composable
internal fun LoginView(
    state: RegisterScreenState.LogIn,
    modifier: Modifier = Modifier,
    onLogin: (String, String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {},
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
) {
    BaseView(
        modifier = modifier.testTag(LOGIN_VIEW_TEST_TAG),
    ) { imeVisible ->
        var isToShow by rememberSaveable { mutableStateOf(false) }
        val (username, password) = state
        val (isValid) = remember(username, password) {
            mutableStateOf(username.isValid && password.isValid)
        }
        MySpacer()
        Text(
            text = stringResource(R.string.login_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
        MySpacer()
        MakeUsernameTextField(
            modifier = Modifier.testTag(USERNAME_TEXT_FIELD),
            value = username.input,
            onUsernameChange = onUsernameChange,
        )
        ShowErrorMessage(username.validation)
        MySpacer()
        MakePasswordTextField(
            value = password.input,
            isToShow = isToShow,
            onPasswordChange = onPasswordChange,
            isToShowChange = { isToShow = !isToShow }
        )
        ShowErrorMessage(password.validation)
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
                        .testTag(LOGIN_VIEW_LOGIN_BUTTON)
                        .width(BUTTON_WIDTH.dp),
                    text = stringResource(R.string.login),
                    enable = isValid,
                    onClick = { onLogin(username.input, password.input) }
                )
                MakeButton(
                    modifier = Modifier
                        .padding(HORIZONTAL_PADDING.dp)
                        .testTag(LOGIN_VIEW_REGISTER_BUTTON)
                        .width(BUTTON_WIDTH.dp),
                    text = stringResource(R.string.register),
                    onClick = onRegisterClick
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
private fun PreviewLoginView() { LoginView(RegisterScreenState.LogIn()) }