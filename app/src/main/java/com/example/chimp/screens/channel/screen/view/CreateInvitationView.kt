package com.example.chimp.screens.channel.screen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChannelInvitationView(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onGenerateClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Channel Invite Code Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // EXPIRE AFTER
        OutlinedTextField(
            value = "30 minutes",
            onValueChange = {},
            label = { Text("Expire After") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = "1",
            onValueChange = {},
            label = { Text("Max Number of Uses") },
            readOnly = true, // Make it read-only
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = "READ_WRITE",
            onValueChange = {},
            label = { Text("Permissions") },
            readOnly = true, // Make it read-only
            modifier = Modifier.fillMaxWidth()
        )

        // Warning message
        Text(
            text = "Warning: Notice that previous invitations created will be deleted.",
            color = Color.Red
        )

        // Suggestion message
        Text(
            text = "Suggestion: If you want to invite multiple users, increment maxUses instead of creating multiple invitation codes."
        )

        // Generate Button
        Button(
            onClick = { onGenerateClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate a New Invitation Code")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChannelInvitationViewPreview() {
    ChannelInvitationView()
}