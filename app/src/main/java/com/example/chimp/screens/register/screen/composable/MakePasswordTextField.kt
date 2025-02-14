package com.example.chimp.screens.register.screen.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.ui.composable.MyTextField

/**
 * The horizontal padding of the password input.
 */
private const val PASSWORD_INPUT_HORIZONTAL_PADDING = 16

/**
 * Creates a password text field.
 *
 * @param value The value of the password.
 * @param isToShow The state of the password.
 * @param onPasswordChange The callback when the password changes.
 * @param isToShowChange The callback when the state of the password changes.
 */
@Composable
internal fun MakePasswordTextField(
    value: String,
    label : String = stringResource(R.string.password),
    isToShow: Boolean,
    onPasswordChange: (String) -> Unit = {},
    isToShowChange: () -> Unit = {},
) {
    MyTextField(
        modifier = Modifier.padding(PASSWORD_INPUT_HORIZONTAL_PADDING.dp),
        label = label,
        value = value,
        onValueChange = onPasswordChange,
        keyBoardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation =
        if (!isToShow) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isToShow) Icons.TwoTone.Lock else Icons.Filled.Lock,
        onTrailingIconChange = isToShowChange
    )
}