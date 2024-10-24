package com.example.chimp.viewModel.state

import com.example.chimp.model.chats.Channel


sealed interface CommunityScreenState {

    data object Loading: CommunityScreenState
    data class Loaded(val channels: List<Channel>): CommunityScreenState
    data class Error(val message: String): CommunityScreenState
}