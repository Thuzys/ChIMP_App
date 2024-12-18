package com.example.chimp.screens.register.screen.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.screens.ui.composable.MyTextField

/**
 * The horizontal padding of the invitation code input.
 */
private const val INVITATION_CODE_INPUT_HORIZONTAL_PADDING = 16

/**
 * A composable that creates an invitation code field.
 *
 * @param value The value of the invitation code.
 * @param onInvitationCodeChange The callback that is called when the invitation code changes.
 */
@Composable
internal fun MakeInvitationCodeField(
    value: String,
    onInvitationCodeChange: (String) -> Unit = {},
){
    MyTextField(
        modifier = Modifier.padding(INVITATION_CODE_INPUT_HORIZONTAL_PADDING.dp),
        label = stringResource(R.string.invite_code),
        value = value,
        onValueChange = onInvitationCodeChange,
    )
}

@Composable
@Preview(showBackground = true)
private fun MakeInvitationCodeFieldPreview() {
    MakeInvitationCodeField(value = "1234567890")
}