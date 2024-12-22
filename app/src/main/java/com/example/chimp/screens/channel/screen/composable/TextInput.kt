package com.example.chimp.screens.channel.screen.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun TextInput(
    modifier: Modifier = Modifier,
    onSendMessage: (String) -> Unit
) {
    var msg by rememberSaveable { mutableStateOf("") }
    val keyBoard = LocalSoftwareKeyboardController.current
    Row(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = msg,
            onValueChange = { msg = it },
            placeholder = { Text("Type a message...") },
            minLines = 1,
            maxLines = 5,
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.clickable { onSendMessage(msg); msg = ""; keyBoard?.hide() }
                )
            },
            keyboardActions = KeyboardActions {
                onSendMessage(msg)
                msg = ""
                keyBoard?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextInputPreview() {
    TextInput {}
}