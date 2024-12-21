package com.example.chimp.screens.channel.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.chimp.screens.channel.screen.view.ChannelInvitationView
import com.example.chimp.screens.channel.screen.view.ScrollingView
import com.example.chimp.screens.channel.screen.view.ShowingInvitationView
import com.example.chimp.screens.channel.viewModel.ChannelViewModel
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import com.example.chimp.screens.ui.views.ErrorView
import com.example.chimp.screens.ui.views.LoadingView

const val CHANNEL_SCREEN_TAG = "ChannelScreen"

@Composable
internal fun ChIMPChannelScreen(
    modifier: Modifier = Modifier,
    vm: ChannelViewModel,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier.testTag(CHANNEL_SCREEN_TAG)
    ) {
        val curr = vm.state.collectAsState().value
        Log.i(CHANNEL_SCREEN_TAG, "State: ${curr::class.simpleName}")
        when (curr) {
            is ChannelScreenState.Initial -> { vm.loadMessages() }
            is ChannelScreenState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize(),)
            }
            is ChannelScreenState.Scrolling -> {
                ScrollingView(
                    modifier = Modifier.fillMaxSize(),
                    state = curr,
                    onBackClick = onBack,
                    onSendMessage = { vm.sendMessage(it) },
                    onDeleteOrLeave = { vm.deleteOrLeave(onBack) },
                    loadMore = vm::loadMore,
                    onCreateInvite = vm::toCreateInvitation
                )
            }
            is ChannelScreenState.Editing -> TODO()
            is ChannelScreenState.Error -> {
                ErrorView(
                    modifier = Modifier.fillMaxSize(),
                    error = curr.error,
                    tryAgain = vm::goBack
                )
            }
            is ChannelScreenState.CreatingInvitation -> {
                ChannelInvitationView(
                    modifier = Modifier.fillMaxSize(),
                    onBackClick = vm::goBack,
                    onGenerateClick = { vm.generateInvitation(it) }
                )
            }
            is ChannelScreenState.ShowingInvitation -> {
                ShowingInvitationView(
                    inviteCode = curr.invitation,
                    onDismiss = vm::goBack
                )
            }
        }
    }
}