package com.example.chimp.chats.viewModel.state

import com.example.chimp.chats.model.channel.Channel

sealed interface ChatsScreenState {
    data object Idle: ChatsScreenState
    data object Loading : ChatsScreenState
    data class ShowChannels(val channels: List<Channel>) : ChatsScreenState
}