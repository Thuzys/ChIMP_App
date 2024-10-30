package com.example.chimp.login.screen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chimp.R
import com.example.chimp.login.viewModel.state.LoginScreenState
import com.example.chimp.ui.composable.GradientBox
import com.example.chimp.ui.composable.MyTextField
import com.example.chimp.ui.utils.isSmallScreen
import com.example.chimp.ui.utils.rememberImeState

/**
 * The tag used to identify the IdleLoginView in automated tests.
 */
const val IDLE_LOGIN_VIEW_TEST_TAG = "IdleLoginViewTestTag"

/**
 * The height of the top box as a percentage of the screen height.
 */
private const val HEIGHT_PERCENTAGE = 0.35f

/**
 * The radius of the rounded corners of the box.
 */
private const val ROUND_CORNER_RADIUS = 40

/**
 * The spacing between the top and bottom boxes.
 */
private const val SMALL_SCREEN_SPACING = 0.05f

/**
 * The spacing between the top and bottom boxes.
 */
private const val LARGE_SCREEN_SPACING = 0.1f

/**
 * The horizontal padding of the username input.
 */
private const val USERNAME_INPUT_HORIZONTAL_PADDING = 16

/**
 * The horizontal padding of the password input.
 */
private const val PASSWORD_INPUT_HORIZONTAL_PADDING = 16

/**
 * The radius of the rounded corners of the box.
 */
private const val ROUNDED_CORNER_RADIUS_ON_BUTTON = 10

/**
 * The horizontal padding of the text field.
 */
private const val HORIZONTAL_PADDING = 10

/**
 * The style of the login button text.
 */
private val loginButtonStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight(500))

private val loginButtonColors = Color(0xFF64B5F6)

@Composable
fun IdleLoginView(
    modifier: Modifier = Modifier,
    vm: LoginScreenState.Idle,
    onUsernameChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    isToShowChange: (LoginScreenState.Idle) -> Unit = {},
    onLoginChange: () -> Unit = {},
) {
    GradientBox(
        modifier = modifier.testTag(IDLE_LOGIN_VIEW_TEST_TAG),
    ) {
        val isImeVisible by rememberImeState()
        val isToShow =
            when(vm) {
                is LoginScreenState.IdleShow -> true
                is LoginScreenState.IdleHide -> false
            }
        val welcomeMessage= stringResource(R.string.welcome_message)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val animatedUpperSectionRation by animateFloatAsState(
                targetValue = if (isImeVisible) 0f else HEIGHT_PERCENTAGE,
                label = "Upper Section Ratio"
            )
            AnimatedVisibility(
                visible = !isImeVisible
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(animatedUpperSectionRation),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = welcomeMessage,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = ROUND_CORNER_RADIUS.dp,
                            topEnd = ROUND_CORNER_RADIUS.dp
                        )
                    )
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isSmallScreen()) Spacer(modifier = Modifier.height(SMALL_SCREEN_SPACING.dp))
                else Spacer(modifier = Modifier.height(LARGE_SCREEN_SPACING.dp))
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                if (isSmallScreen()) Spacer(modifier = Modifier.height(SMALL_SCREEN_SPACING.dp))
                else Spacer(modifier = Modifier.height(LARGE_SCREEN_SPACING.dp))
                MyTextField(
                    modifier = Modifier.padding(USERNAME_INPUT_HORIZONTAL_PADDING.dp),
                    label = stringResource(R.string.username),
                    value = vm.username,
                    onValueChange = onUsernameChange,
                )
                if (isSmallScreen()) Spacer(modifier = Modifier.height(SMALL_SCREEN_SPACING.dp))
                else Spacer(modifier = Modifier.height(LARGE_SCREEN_SPACING.dp))
                MyTextField(
                    modifier = Modifier.padding(PASSWORD_INPUT_HORIZONTAL_PADDING.dp),
                    label = stringResource(R.string.password),
                    value = vm.password,
                    onValueChange = onPasswordChange,
                    keyBoardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation =
                    if (!isToShow) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = if (isToShow) Icons.TwoTone.Lock else Icons.Filled.Lock,
                    onTrailingIconChange = { isToShowChange(vm) }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(HORIZONTAL_PADDING.dp),
                    contentAlignment =
                    if (isImeVisible) Alignment.TopCenter else Alignment.CenterStart
                ) {
                    Button(
                        onClick = onLoginChange,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = vm.isValid,
                        colors = ButtonDefaults.buttonColors(containerColor = loginButtonColors),
                        shape = RoundedCornerShape(ROUNDED_CORNER_RADIUS_ON_BUTTON.dp)
                    ) {
                        Text(stringResource(R.string.login), style = loginButtonStyle)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewIdleLoginView() {
    var vm: LoginScreenState.Idle by remember {
        mutableStateOf(LoginScreenState.IdleHide("", ""))
    }
    IdleLoginView(
        vm = vm,
        onUsernameChange = { vm = vm.updateUsername(it) },
        onPasswordChange = { vm = vm.updatePassword(it) },
        isToShowChange = { vm =
            if (it is LoginScreenState.IdleShow) LoginScreenState.IdleHide(it.username, it.password)
            else LoginScreenState.IdleShow(it.username, it.password)
         },
    )
}