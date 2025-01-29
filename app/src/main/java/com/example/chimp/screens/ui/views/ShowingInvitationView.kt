package com.example.chimp.screens.ui.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chimp.R

const val SHOWING_INVITATION_VIEW_TAG = "ShowingInvitationView"
private const val FONT_SIZE = 14
private const val PADDING = 16
private const val BUTTON_PADDING = 8

@Composable
fun ShowingInvitationView(
    inviteCode: String,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Column(
        modifier = modifier
            .testTag(SHOWING_INVITATION_VIEW_TAG),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = stringResource(R.string.channel_invitation_code),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = inviteCode,
                    fontSize = FONT_SIZE.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .padding(PADDING.dp),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(inviteCode))
                        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(BUTTON_PADDING.dp)
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Copy to clipboard"
                    )
                }
            },
        )
    }
}

@Preview
@Composable
private fun ShowingInvitationViewPreview() {
    ShowingInvitationView(inviteCode = "123456")
}