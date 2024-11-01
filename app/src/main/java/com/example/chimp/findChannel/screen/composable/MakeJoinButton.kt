package com.example.chimp.findChannel.screen.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MakeJoinButton(joinChannel: () -> Unit) {
    Button(
        onClick = joinChannel,
    ) {
        Text("Join")
    }
}