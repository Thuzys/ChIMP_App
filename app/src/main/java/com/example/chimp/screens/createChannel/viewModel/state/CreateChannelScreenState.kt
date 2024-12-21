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
        override val channelName: String,
    ) : EditingState

    data class Validated(
        override val channelName: String,
    ) : EditingState

    data class NotValidated(
        override val channelName: String
    ) : EditingState

    data object Submit : CreateChannelScreenState

    interface EditingState : CreateChannelScreenState{
        val channelName: String
    }
}