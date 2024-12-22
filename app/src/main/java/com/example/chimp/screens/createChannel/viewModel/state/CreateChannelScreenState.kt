package com.example.chimp.screens.createChannel.viewModel.state

import com.example.chimp.models.DataInput
import com.example.chimp.models.errors.ResponseError

interface CreateChannelScreenState {

    data object BackToRegistration : CreateChannelScreenState

    data class Error(
        val error: ResponseError,
        val goBack: CreateChannelScreenState
    ) : CreateChannelScreenState

    data class Editing(
        val channelName: DataInput = DataInput.initialState,
    ) : CreateChannelScreenState


    data object Submit : CreateChannelScreenState

}