package com.example.chimp.screens.findChannel.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.screens.findChannel.screen.view.FindChannelView
import com.example.chimp.screens.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.ui.views.ErrorView
import com.example.chimp.screens.ui.views.LoadingView

@Composable
fun ChIMPFindChannelScreen(
    modifier: Modifier = Modifier,
    viewModel: FindChannelViewModel,
    onJoinChannel: () -> Unit,
) {
    val curr by viewModel.state.collectAsState()

    when (curr) {
        is FindChannel.FindChannelIdle -> {
            FindChannelBase(modifier, (curr as FindChannel.FindChannelIdle), viewModel)
        }
        is FindChannelScreenState.Error -> {
            ErrorView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                error = (curr as FindChannelScreenState.Error).error,
                tryAgain = viewModel::toFindChannel,
            )
        }
        is FindChannelScreenState.Joined -> {
            onJoinChannel()
        }
        is FindChannelScreenState.Loading -> {
            LoadingView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            )
        }
    }
}


@Composable
private fun FindChannelBase(
    modifier: Modifier,
    curr: FindChannel,
    viewModel: FindChannelViewModel
) {
    FindChannelView(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        vm = curr,
        onJoin = viewModel::joinChannel,
        onSearch = viewModel::findChannel,
    )
}