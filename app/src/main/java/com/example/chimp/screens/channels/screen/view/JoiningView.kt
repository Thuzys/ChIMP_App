package com.example.chimp.screens.channels.screen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chimp.R
import com.example.chimp.screens.ui.composable.MyTextField

private const val PADDING = 16
private const val FONT_SIZE = 20

@Composable
fun JoiningView(
    modifier: Modifier = Modifier,
    onJoinChannel: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val invitationCode = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(PADDING.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.join_channel_label),
                fontSize = FONT_SIZE.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .clickable { onBackClick() },
                imageVector = Icons.Default.Close,
                contentDescription = "Back",
            )
        }
        MyTextField(
            modifier = Modifier.fillMaxWidth(),
            value = invitationCode.value,
            onValueChange = { invitationCode.value = it },
            label = stringResource(R.string.insert_invitation_code),
        )

        Button(
            onClick = { onJoinChannel(invitationCode.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.join_channel_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JoiningViewPreview() {
    JoiningView(
        onJoinChannel = {},
        onBackClick = {}
    )
}