package com.example.chimp.screens.createChannel.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.chimp.screens.createChannel.screen.view.EditingView
import com.example.chimp.screens.createChannel.viewModel.CreateChannelViewModel
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState
import com.example.chimp.screens.findChannel.activity.FindChannelActivity
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.ui.views.ErrorView
import com.example.chimp.screens.ui.views.LoadingView


const val CREATE_CHANNEL_SCREEN_TAG = "CreateChannelScreen"

@Composable
fun ChIMPCreateChannelScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateChannelViewModel,
    onFindChannelNavigate: () -> Unit,
    onAboutNavigate: () -> Unit,
    onChatsNavigate: () -> Unit,
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
        Log.i(CREATE_CHANNEL_SCREEN_TAG, "State: ${curr::class.simpleName}")
        CreateChannelScreenAux(
            bottomBar = {
                MenuBottomBar(
                    createChannelIsEnable = false,
                    onMenuClick = onChatsNavigate,
                    aboutClick = onAboutNavigate,
                    findChannelClick = onFindChannelNavigate
                )
            }
        ) { modifier ->

            when(curr) {
                is CreateChannelScreenState.Editing -> {
                    EditingView(
                        state = curr,
                        showMessage = null,
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(),
                        onSubmit = viewModel::submitChannel,
                        onChannelNameChange = viewModel::updateChannelName
                    )
                }
                is CreateChannelScreenState.Validated -> {
                    EditingView(
                        state = curr,
                        showMessage = false,
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(),
                        onSubmit = viewModel::submitChannel,
                        onChannelNameChange = viewModel::updateChannelName
                    )
                }
                is CreateChannelScreenState.NotValidated -> {
                    EditingView(
                        state = curr,
                        showMessage = true,
                        modifier = modifier
                            .fillMaxSize()
                            .wrapContentSize(),
                        onSubmit = viewModel::submitChannel,
                        onChannelNameChange = viewModel::updateChannelName
                    )
                }
                is CreateChannelScreenState.Error -> {
                    ErrorView(
                        error = curr.error,
                        modifier = modifier,
                        tryAgain = { viewModel.goBack() }
                    )
                }
                is CreateChannelScreenState.Submit -> {
                    LoadingView(modifier = modifier)
                }
                is CreateChannelScreenState.BackToRegistration -> {
                    registerNavigate()
                }

            }
        }
    }
}

@Composable
private fun CreateChannelScreenAux(
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        bottomBar = bottomBar
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}