package com.example.chimp.findChannel.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.findChannel.screen.view.FindChannelView
import com.example.chimp.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.findChannel.viewModel.state.FindChannel
import com.example.chimp.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.login.screen.view.ErrorView
import com.example.chimp.login.screen.view.LoadingView

@Composable
fun ChIMPFindChannelScreen(
    modifier: Modifier = Modifier,
    viewModel: FindChannelViewModel,
    onJoinChannel: () -> Unit,
) {
    when(val curr = viewModel.state) {
        is FindChannel -> {
            FindChannelBase(modifier, curr, viewModel)
        }
        is FindChannelScreenState.Error -> {
            ErrorView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                error = curr.error,
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
        is FindChannelScreenState.Success -> {
            FindChannelBase(modifier, curr, viewModel)
        }
    }
}

@Composable
private fun FindChannelBase(
    modifier: Modifier,
    curr: FindChannelScreenState,
    viewModel: FindChannelViewModel
) {
    FindChannelView(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        vm = curr,
        onValueChange = viewModel::updateSearchChannelInput,
        onJoin = viewModel::joinChannel,
        onSearch = viewModel::findChannel,
    )
}