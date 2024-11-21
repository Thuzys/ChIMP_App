package com.example.chimp.screens.findChannel.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.findChannel.viewModel.state.FindChannel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class FindChannelViewModel(
    private val service: FindChannelService
) : ViewModel() {
    var state: FindChannelScreenState by mutableStateOf(FindChannelScreenState.Loading)
        private set

    fun joinChannel(channelId: UInt, invitationCode: String?) {
        val curr = state
        if (curr !is FindChannel.FindChannelIdle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.joinChannel(channelId, invitationCode)) {
                is Success -> FindChannelScreenState.Joined(channelId)

                is Failure ->
                    FindChannelScreenState.Error(
                        ResponseErrors(
                            result.value.cause,
                            result.value.urlInfo
                        )
                    )
            }
        }
    }

    fun findChannel(channelName: String) {
        val curr = state
        if (curr !is FindChannel.FindChannelIdle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.findChannelByName(ChannelName(channelName))) {
                is Success -> FindChannel.FindChannelIdle(
                    publicChannels = listOf(result.value)
                )

                is Failure -> FindChannelScreenState.Error(
                    ResponseErrors(
                        result.value.cause,
                        result.value.urlInfo
                    )
                )
            }
        }
    }

    fun getChannels(offset: UInt?, limit: UInt?) {
        val curr = state
        if (curr !is FindChannel.FindChannelIdle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.getChannels(offset, limit)) {
                is Success ->
                    FindChannel.FindChannelIdle(
                        publicChannels = result.value.toList()
                    )

                is Failure -> FindChannelScreenState.Error(
                    ResponseErrors(
                        result.value.cause,
                        result.value.urlInfo
                    )
                )
            }
        }
    }

    fun updatePublicChannels(publicChannels: List<ChannelBasicInfo>) {
        val curr = state
        if (curr is FindChannel.FindChannelIdle) {
            state = curr.updatePublicChannels(publicChannels)
        }
    }

    fun toFindChannel() {
        if (state !is FindChannel) {
            state = FindChannel.FindChannelIdle(publicChannels = emptyList())
        }
    }
}