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
import com.example.chimp.screens.channels.screen.view.ChannelInfoView
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
    onRegisterNavigate: () -> Unit
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
        when (curr) {
            is FindChannelScreenState.Initial -> {
                viewModel.getChannels()
            }

            is FindChannelScreenState.Loading -> {
                FindChannelScreenAux(
                    bottomBar = {
                        MenuBottomBar(
                            findChannelIsEnable = false,
                            onMenuClick = onChatsNavigate,
                            aboutClick = onAboutNavigate
                        )
                    }
                ) {
                    LoadingView(
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                    )
                }
            }

            is FindChannelScreenState.Scrolling -> {
                FindChannelScreenAux(
                    bottomBar = {
                        MenuBottomBar(
                            findChannelIsEnable = false,
                            onMenuClick = onChatsNavigate,
                            aboutClick = onAboutNavigate
                        )
                    }
                ) { modifier ->
                    ScrollingView(
                        modifier = modifier,
                        publicChannels = curr,
                        onLogout = viewModel::logout,
                        onInfoClick = viewModel::toChannelInfo,
                        onReload = viewModel::getChannels,
                        onReloadSearching = viewModel::getChannels,
                        onJoin = viewModel::joinChannel,
                        onSearchChange = viewModel::updateSearchText,
                        onLoadMore = viewModel::loadMore,
                        onLoadMoreSearching = viewModel::loadMore
                    )

                }
            }

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

            is FindChannelScreenState.BackToRegistration -> registerNavigate()
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