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
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.failure
import com.example.chimp.screens.register.model.DataInput
import com.example.chimp.screens.register.model.InputValidation
import com.example.chimp.screens.register.screen.composable.BaseView
import com.example.chimp.screens.register.screen.composable.InputErrorDisplay
import com.example.chimp.screens.register.screen.composable.MakeInvitationCodeField
import com.example.chimp.screens.register.screen.composable.MakePasswordTextField
import com.example.chimp.screens.register.screen.composable.MakeUsernameTextField
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Register
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
 * @param onRegister the function to be invoked when the user wants to register
 * @param onLoginClick the function to be invoked when the user wants to login
 */
@Composable
internal fun RegisterView(
    state: Register,
    modifier: Modifier = Modifier,
    onRegister: (String, String, String) -> Unit = { _, _, _ -> },
    onLoginClick: () -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onUsernameChange: (String) -> Unit = {},
) {
    BaseView(
        modifier = modifier.testTag(REGISTER_VIEW_TEST_TAG),
    ) { imeVisible ->
        val (username, password) = state
        val (confirmPass, setConfirmPass) = rememberSaveable { mutableStateOf("") }
        val (invitationCode, setInvitationCode) = rememberSaveable { mutableStateOf("") }
        val (isValid) = remember(username, password, confirmPass, invitationCode) {
            mutableStateOf(
                username.isValid
                        && password.isValid
                        && confirmPass === password.input
                        && invitationCode !== ""
            )
        }
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
            value = username.input,
            onUsernameChange = onUsernameChange,
        )
        ShowErrorMessage(username.validation)
        MySpacer()
        MakePasswordTextField(
            value = password.input,
            isToShow = isToShowPass,
            onPasswordChange = onPasswordChange,
            isToShowChange = { isToShowPass = !isToShowPass }
        )
        ShowErrorMessage(password.validation)
        MySpacer()
        MakePasswordTextField(
            value = confirmPass,
            label = stringResource(R.string.confirm_password),
            isToShow = isToShowConfirmPass,
            onPasswordChange = setConfirmPass,
            isToShowChange = { isToShowConfirmPass = !isToShowConfirmPass }
        )
        ShowConfirmPasswordError(password.input, confirmPass)
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
                    onClick = { onRegister(username.input, password.input, invitationCode) }
                )
                MakeButton(
                    modifier = Modifier
                        .width(BUTTON_WIDTH.dp)
                        .padding(HORIZONTAL_PADDING.dp)
                        .testTag(REGISTER_VIEW_LOGIN_BUTTON_TEST_TAG),
                    text = stringResource(R.string.login),
                    onClick = onLoginClick
                )
            }
        }
    }
}

@Composable
private fun ShowErrorMessage(message: InputValidation) {
    if (message is Failure<String>) {
        InputErrorDisplay(
            modifier = Modifier.fillMaxWidth(),
            message = message.value
        )
    }
}

@Composable
private fun ShowConfirmPasswordError(password: String, confirmPass: String) {
    if (password != confirmPass && confirmPass.isNotEmpty()) {
        InputErrorDisplay(
            modifier = Modifier.fillMaxWidth(),
            message = stringResource(R.string.password_mismatch)
        )
    }
}

@Preview
@Composable
private fun PreviewRegisterView() {
    val username = DataInput("dummy", failure("Username is invalid"))
    RegisterView(Register(username))
}