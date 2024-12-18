package com.example.chimp.screens.findChannel.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import kotlinx.coroutines.launch

class FindChannelViewModel(
    private val service: FindChannelService
) : ViewModel() {
    var state: FindChannelScreenState by mutableStateOf(FindChannelScreenState.Loading)
        private set

    fun joinChannel(channelId: UInt) {
        val curr = state
        if (curr !is FindChannelScreenState.Idle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.joinChannel(channelId)) {
                is Success -> FindChannelScreenState.Idle(publicChannels = curr.publicChannels)

                is Failure ->
                    FindChannelScreenState.Error(
                        ResponseError(
                            result.value.cause,
                            result.value.urlInfo
                        )
                    )
            }
        }
    }

    fun findChannel(channelName: String) {
        val curr = state
        if (curr !is FindChannelScreenState.Idle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.findChannelsByPartialName(ChannelName(channelName))) {
                is Success -> FindChannelScreenState.Idle(
                    publicChannels = result.value
                )

                is Failure -> FindChannelScreenState.Error(
                    ResponseError(
                        result.value.cause,
                        result.value.urlInfo
                    )
                )
            }
        }
    }

    fun getChannels(offset: UInt?, limit: UInt?) {
        val curr = state
        if (curr !is FindChannelScreenState.Idle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.getChannels(offset, limit)) {
                is Success ->
                    FindChannelScreenState.Idle(
                        publicChannels = result.value
                    )

                is Failure -> FindChannelScreenState.Error(
                    ResponseError(
                        result.value.cause,
                        result.value.urlInfo
                    )
                )
            }
        }
    }
}