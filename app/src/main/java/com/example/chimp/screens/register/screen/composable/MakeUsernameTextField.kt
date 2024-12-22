package com.example.chimp.screens.register.screen.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.ui.composable.MyTextField

/**
 * The horizontal padding of the username input.
 */
private const val USERNAME_INPUT_HORIZONTAL_PADDING = 16

/**
 * Creates a username text field.
 * @param modifier the modifier to be applied to the Text field.
 * @param value The value of the username text field.
 * @param onUsernameChange The function to call when the username changes.
 */
@Composable
internal fun MakeUsernameTextField(
    modifier: Modifier = Modifier,
    value: String,
    onUsernameChange: (String) -> Unit
) {
    MyTextField(
        modifier = modifier.padding(USERNAME_INPUT_HORIZONTAL_PADDING.dp),
        label = stringResource(R.string.username),
        value = value,
        onValueChange = onUsernameChange,
    )
}