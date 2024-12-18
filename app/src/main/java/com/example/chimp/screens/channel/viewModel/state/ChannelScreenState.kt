package com.example.chimp.screens.channel.viewModel.state

import com.example.chimp.models.errors.ResponseError
import com.example.chimp.screens.channel.model.Message
import com.example.chimp.models.channel.ChannelInfo
import kotlinx.coroutines.flow.Flow

interface ChannelScreenState {
    data object Initial : ChannelScreenState
    data object Loading : ChannelScreenState
    data class Scrolling(
        val messages: Flow<List<Message>>,
        val hasMore: Flow<Boolean>,
    )
    data class Error(
        val error: ResponseError,
        val previous: ChannelScreenState,
    )
    data class Editing(
        val channel: ChannelInfo,
        val previous: ChannelScreenState,
    )
}