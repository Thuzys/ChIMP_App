package com.example.chimp.screens.chats.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.chimp.screens.chats.screen.view.ChatView
import com.example.chimp.screens.chats.screen.view.IdleView
import com.example.chimp.screens.chats.viewModel.ChatsViewModel
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState
import com.example.chimp.screens.ui.composable.MenuBottomBar

const val CHATS_SCREEN_TAG = "ChatsScreen"

@Composable
internal fun ChIMPChatsScreen(
    modifier: Modifier = Modifier,
    vm: ChatsViewModel,
    onFindChannelNavigate: () -> Unit = {},
    onAboutNavigate: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        val curr = vm.state.collectAsState().value
        Log.i(CHATS_SCREEN_TAG, "State: ${curr::class.simpleName}")
        when(curr) {
            is ChatsScreenState.Initial -> { vm.loadChannels() }
            is ChatsScreenState.Loading -> TODO()
            is ChatsScreenState.Idle -> {
                ChatsScreenAux(
                    bottomBar = {
                        MenuBottomBar(
                            chatsIsEnable = false,
                            addChannelClick = onFindChannelNavigate,
                            aboutClick = onAboutNavigate
                        )
                    }
                ) { modifier ->
                    IdleView(
                        modifier = modifier,
                        chats = curr,
                        onInfoClick = { vm.toChatInfo(it) },
                        onChannelClick = { vm.toChatSelected(it) },
                        onDeleteChannel = { vm.deleteChannel(it) }
                    )
                }
            }
            is ChatsScreenState.Error -> TODO()
            is ChatsScreenState.ChatSelected -> {
                ChatsScreenAux {
                     ChatView(
                         state = curr,
                         onBackClick = { vm.toIdle() },
                         onInfoClick = { vm.toChannelInfo(it) },
                         onSendMessage = { vm.sendMessage(it) }
                     )
                }
            }
            is ChatsScreenState.ChannelInfo -> TODO()
        }
    }
}

@Composable
private fun ChatsScreenAux(
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        bottomBar = bottomBar
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}