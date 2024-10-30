package com.example.chimp.viewModel.state

import com.example.chimp.model.chats.Channel

sealed interface ChatsScreenState {
    data object Idle: ChatsScreenState
    data object Loading : ChatsScreenState
    data class ShowChannels(val channels: List<Channel>) : ChatsScreenState
}