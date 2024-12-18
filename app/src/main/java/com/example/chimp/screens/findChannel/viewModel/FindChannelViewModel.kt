package com.example.chimp.screens.findChannel.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelItem
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.findChannel.viewModel.state.FindChannel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class FindChannelViewModel(
    private val service: FindChannelService
) : ViewModel() {
    private val _state = MutableStateFlow<FindChannelScreenState>(FindChannel.FindChannelIdle(null))
    val state: StateFlow<FindChannelScreenState> = _state

    fun joinChannel(channelId: UInt, invitationCode: String?) =
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannel.FindChannelIdle) return@launch
            _state.emit(FindChannelScreenState.Loading)
            _state.emit(
                when (val result = service.joinChannel(channelId, invitationCode)) {
                    is Success -> FindChannelScreenState.Joined(channelId)

                    is Failure ->
                        FindChannelScreenState.Error(
                            ResponseErrors(
                                result.value.cause,
                                result.value.urlInfo
                            )
                        )
                }
            )
        }

    fun findChannel(channelName: String) =
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannel.FindChannelIdle) return@launch
            _state.emit(FindChannelScreenState.Loading)
            _state.emit(
                when (val result = service.findChannelByName(ChannelName(channelName))) {
                    is Success ->
                        FindChannel.FindChannelIdle(flowOf(listOfNotNull(result.value)))

                    is Failure -> FindChannelScreenState.Error(
                        ResponseErrors(
                            result.value.cause,
                            result.value.urlInfo
                        )
                    )
                }
            )
        }

    fun getChannels(offset: UInt?, limit: UInt?) =
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannel.FindChannelIdle) return@launch
            _state.emit(FindChannelScreenState.Loading)
            _state.emit(
                when (val result = service.getChannels(offset, limit)) {
                    is Success ->
                        FindChannel.FindChannelIdle(result.value)

                    is Failure -> FindChannelScreenState.Error(
                        ResponseErrors(
                            result.value.cause,
                            result.value.urlInfo
                        )
                    )
                }
            )
        }

    fun updatePublicChannels(publicChannels: List<FindChannelItem>) =
        viewModelScope.launch {
            val curr = state.value
            if (curr is FindChannel.FindChannelIdle) {
                _state.emit(curr.updatePublicChannels(flowOf(publicChannels)))
            }
        }

    fun toFindChannel() =
        viewModelScope.launch {
            if (state.value !is FindChannel.FindChannelIdle) {
                _state.emit(FindChannel.FindChannelIdle(null))
            }
        }
}