package com.example.chimp.screens.findChannel.screen.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun DisconnectedView(
    modifier: Modifier = Modifier,
    tryAgain: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxSize(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No internet connection")
            Button(
                onClick = tryAgain,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Try Again")
            }
        }
    }
}