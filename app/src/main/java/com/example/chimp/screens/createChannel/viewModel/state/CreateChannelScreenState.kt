package com.example.chimp.screens.createChannel.viewModel.state

import com.example.chimp.models.errors.ResponseError

interface CreateChannelScreenState {

    data object Loading : CreateChannelScreenState

    data class Error(
        val error: ResponseError,
    ) : CreateChannelScreenState

    data object Successfull : CreateChannelScreenState

    data class Editing(
        val channelName: String,
    ) : CreateChannelScreenState

    data class Validated(
        val channelName: String,
    ) : CreateChannelScreenState

    data object NotValidated : CreateChannelScreenState

    data class Submit(
        val channelName: String,
    ) : CreateChannelScreenState

}