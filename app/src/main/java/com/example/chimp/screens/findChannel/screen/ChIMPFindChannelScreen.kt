package com.example.chimp.screens.findChannel.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.screens.ui.views.ChannelInfoView
import com.example.chimp.screens.findChannel.screen.view.DisconnectedView
import com.example.chimp.screens.findChannel.screen.view.ErrorView
import com.example.chimp.screens.findChannel.screen.view.ScrollingView
import com.example.chimp.screens.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.ui.views.LoadingView

const val FIND_CHANNEL_SCREEN_TAG = "FindChannelScreen"

@Composable
fun ChIMPFindChannelScreen(
    modifier: Modifier = Modifier,
    viewModel: FindChannelViewModel,
    onChatsNavigate: () -> Unit,
    onAboutNavigate: () -> Unit,
    onRegisterNavigate: () -> Unit,
    onJoinNavigate : () -> Unit,
    status: ConnectivityObserver.Status = ConnectivityObserver.Status.CONNECTED
) {

    val registerNavigate = {
        onRegisterNavigate()
        viewModel.reset()
    }

    Column(
        modifier = modifier
    ) {
        val curr = viewModel.state.collectAsState().value
        Log.i(FIND_CHANNEL_SCREEN_TAG, "State: ${curr::class.simpleName}")
        FindChannelScreenAux(
            bottomBar = {
                MenuBottomBar(
                    findChannelIsEnable = false,
                    onMenuClick = onChatsNavigate,
                    aboutClick = onAboutNavigate
                )
            }
        ) { modifier ->
            if (status == ConnectivityObserver.Status.DISCONNECTED) {
                DisconnectedView { viewModel.reset() }
            } else {
                when(curr) {
                    FindChannelScreenState.BackToRegistration -> registerNavigate()
                    is FindChannelScreenState.Error -> {
                        ErrorView(
                            state = curr,
                            modifier = modifier,
                            close = viewModel::goBack,
                        )
                    }
                    is FindChannelScreenState.Info -> {
                        ChannelInfoView(
                            modifier = Modifier.fillMaxSize(),
                            channel = curr.channel,
                            onGoBackClick = viewModel::goBack
                        )
                    }
                    FindChannelScreenState.Initial -> viewModel.getChannels()

                    FindChannelScreenState.Loading -> {
                        LoadingView(
                            modifier = modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center),
                        )
                    }
                    is FindChannelScreenState.Scrolling -> {
                        ScrollingView(
                            modifier = modifier,
                            publicChannels = curr,
                            onLogout = viewModel::logout,
                            onInfoClick = viewModel::toChannelInfo,
                            onReload = viewModel::reset,
                            onJoin = {viewModel.joinChannel(it, onJoinNavigate)},
                            onSearchChange = viewModel::updateSearchText,
                            onLoadMore = viewModel::loadMore,
                            onLoadMoreSearching = viewModel::loadMore,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FindChannelScreenAux(
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        bottomBar = bottomBar
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}