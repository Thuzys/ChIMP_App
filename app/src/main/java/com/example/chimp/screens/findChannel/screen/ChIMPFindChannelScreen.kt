package com.example.chimp.screens.findChannel.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.screens.findChannel.screen.view.IdleView
import com.example.chimp.screens.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.ui.views.LoadingView

@Composable
fun ChIMPFindChannelScreen(
    modifier: Modifier = Modifier,
    viewModel: FindChannelViewModel,
    onChatsNavigate: () -> Unit,
    onAboutNavigate: () -> Unit
) {
    when (val curr = viewModel.state) {
        is FindChannelScreenState.Idle -> {
            FindChannelScreenAux(
                bottomBar = {
                    MenuBottomBar(
                        addChannelIsEnable = false,
                        onMenuClick = onChatsNavigate,
                        aboutClick = onAboutNavigate
                    )
                }
            ) {
                IdleView(
                    modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    state = curr,
                    onJoin = viewModel::joinChannel,
                    onSearch = viewModel::findChannel,
                )
            }
        }

        is FindChannelScreenState.Error -> TODO()

        is FindChannelScreenState.Loading -> {
            FindChannelScreenAux(
                bottomBar = {
                    MenuBottomBar(
                        addChannelIsEnable = false,
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