package com.example.chimp.findChannel.screen.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MakeJoinButton(onJoin: () -> Unit) {
    Button(
        onClick = onJoin,
    ) {
        Text("Join")
    }
}