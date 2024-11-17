package com.example.chimp.screens.chats.viewModel.state

import com.example.chimp.screens.chats.model.channel.Channel

sealed interface ChatsScreenState {
    data object Idle: ChatsScreenState
    data object Loading : ChatsScreenState
    data class ShowChannels(val channels: List<Channel>) : ChatsScreenState
}