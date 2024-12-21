package com.example.chimp.screens.createChannel.viewModel.state

import com.example.chimp.models.errors.ResponseError

interface CreateChannelScreenState {
    data object BackToRegistration : CreateChannelScreenState
    data class Error(
        val error: ResponseError,
        val goBack: CreateChannelScreenState
    ) : CreateChannelScreenState
    data object Successful : CreateChannelScreenState
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