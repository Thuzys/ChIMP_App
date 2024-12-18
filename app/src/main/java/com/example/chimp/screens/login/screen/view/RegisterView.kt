package com.example.chimp.screens.login.screen.view

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
import com.example.chimp.screens.login.screen.composable.BaseView
import com.example.chimp.screens.login.screen.composable.MakeInvitationCodeField
import com.example.chimp.screens.login.screen.composable.MakePasswordTextField
import com.example.chimp.screens.login.screen.composable.MakeUsernameTextField
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Register
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Register.Companion.isValid
import com.example.chimp.screens.ui.composable.MakeButton
import com.example.chimp.screens.ui.composable.MySpacer

/**
 * The tag used to identify the register view in automated tests.
 */
const val REGISTER_VIEW_TEST_TAG = "RegisterViewTestTag"

/**
 * The horizontal padding of the text field.
 */
private const val HORIZONTAL_PADDING = 10

/**
 * The width of the button.
 */
private const val BUTTON_WIDTH = 150

/**
 * The tag used to identify the register button in automated tests.
 */
const val REGISTER_VIEW_REGISTER_BUTTON_TEST_TAG = "RegisterViewRegisterButtonTestTag"

/**
 * The tag used to identify the login button in automated tests.
 */
const val REGISTER_VIEW_LOGIN_BUTTON_TEST_TAG = "RegisterViewLoginButtonTestTag"

/**
 * The top padding of the text field.
 */
private const val TOP_TEXT_PADDING = 60

/**
 * The RegisterView composable that displays the register screen.
 *
 * @param modifier the modifier to be applied to the composable
 * @param onRegisterChange the function to be invoked when the user wants to register
 * @param onLoginChange the function to be invoked when the user wants to login
 */
@Composable
internal fun RegisterView(
    state: Register,
    modifier: Modifier = Modifier,
    onRegisterChange: (String, String, String) -> Unit = {_, _, _ -> },
    onLoginChange: (String, String) -> Unit = {_, _ ->},
) {
    BaseView(
        modifier = modifier.testTag(REGISTER_VIEW_TEST_TAG),
    ) { imeVisible ->
        val (username, setUsername) = rememberSaveable { mutableStateOf(state.username) }
        val (password, setPassword) = rememberSaveable { mutableStateOf(state.password) }
        val (confirmPass, setConfirmPass) = rememberSaveable { mutableStateOf("") }
        val (isValid, setIsValid) = rememberSaveable {
            mutableStateOf(isValid(state.username, state.password, state.password))
        }
        val (invitationCode, setInvitationCode) = rememberSaveable { mutableStateOf("") }
        var isToShowPass by rememberSaveable { mutableStateOf(false) }
        var isToShowConfirmPass by rememberSaveable { mutableStateOf(false) }

        MySpacer()
        Text(
            modifier = Modifier.padding(top = TOP_TEXT_PADDING.dp),
            text = stringResource(R.string.register_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
        )
        MySpacer()
        MakeUsernameTextField(
            value = username,
            onUsernameChange = { setUsername(it); setIsValid(isValid(it, password, confirmPass)) },
        )
        MySpacer()
        MakePasswordTextField(
            value = password,
            isToShow = isToShowPass,
            onPasswordChange = { setPassword(it); setIsValid(isValid(username, it, confirmPass)) },
            isToShowChange = { isToShowPass = !isToShowPass }
        )
        MySpacer()
        MakePasswordTextField(
            value = confirmPass,
            label = stringResource(R.string.confirm_password),
            isToShow = isToShowConfirmPass,
            onPasswordChange = { setConfirmPass(it); setIsValid(isValid(username, password, it)) },
            isToShowChange = { isToShowConfirmPass = !isToShowConfirmPass }
        )
        MySpacer()
        MakeInvitationCodeField(
            value = invitationCode,
            onInvitationCodeChange = setInvitationCode
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
                    .fillMaxWidth(animatedButtonsVisibility)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MakeButton(
                    modifier = Modifier
                        .padding(HORIZONTAL_PADDING.dp)
                        .width(BUTTON_WIDTH.dp)
                        .testTag(REGISTER_VIEW_REGISTER_BUTTON_TEST_TAG),
                    text = stringResource(R.string.register),
                    enable = isValid,
                    onClick = { onRegisterChange(username, password, invitationCode) }
                )
                MakeButton(
                    modifier = Modifier
                        .width(BUTTON_WIDTH.dp)
                        .padding(HORIZONTAL_PADDING.dp)
                        .testTag(REGISTER_VIEW_LOGIN_BUTTON_TEST_TAG),
                    text = stringResource(R.string.login),
                    onClick = { onLoginChange(username, password) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRegisterView() { RegisterView(Register()) }