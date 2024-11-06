package com.example.chimp.login.screen.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chimp.R
import com.example.chimp.ui.composable.MyTextField

/**
 * The horizontal padding of the invitation code input.
 */
private const val INVITATION_CODE_INPUT_HORIZONTAL_PADDING = 16

@Composable
fun MakeInvitationCodeField(
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