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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

const val TEXT_INPUT_TAG = "TextInputTestTag"
const val SEND_BUTTON_TAG = "SendButtonTestTag"

const val MIN_LINES = 1
const val MAX_LINES = 5
const val CORNER_RADIUS = 16
const val PADDING = 8

@Composable
internal fun    TextInput(
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
            minLines = MIN_LINES,
            maxLines = MAX_LINES,
            trailingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = null,
                    modifier = Modifier
                        .testTag(SEND_BUTTON_TAG)
                        .clickable { onSendMessage(msg); msg = ""; keyBoard?.hide() }
                )
            },
            keyboardActions = KeyboardActions {
                onSendMessage(msg)
                msg = ""
                keyBoard?.hide()
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TEXT_INPUT_TAG)
                .clip(RoundedCornerShape(CORNER_RADIUS.dp))
                .padding(PADDING.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextInputPreview() {
    TextInput {}
}