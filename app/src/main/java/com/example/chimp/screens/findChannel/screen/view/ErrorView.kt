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
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState

@Composable
internal fun ErrorView(
    state: FindChannelScreenState.Error,
    modifier: Modifier = Modifier,
    close: () -> Unit
) {
    val error = state.error

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxSize(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = error.cause)
            Button(
                onClick = close,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Close")
            }
        }
    }
}