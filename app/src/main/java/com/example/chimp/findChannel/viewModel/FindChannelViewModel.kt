package com.example.chimp.findChannel.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.about.viewModel.state.AboutScreenState
import com.example.chimp.chats.model.channel.ChannelName
import com.example.chimp.either.Failure
import com.example.chimp.either.Success
import com.example.chimp.findChannel.model.FindChannelItem
import com.example.chimp.findChannel.model.FindChannelService
import com.example.chimp.findChannel.viewModel.state.FindChannel
import com.example.chimp.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.login.viewModel.state.LoginScreenState
import com.example.chimp.model.errors.ResponseErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class FindChannelViewModel(
    private val service: FindChannelService
) : ViewModel() {
    var state: FindChannelScreenState by mutableStateOf(FindChannelScreenState.Loading)
        private set

    fun joinChannel(channelId: UInt) {
        val curr = state
        if (curr !is FindChannel.FindChannelIdle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.joinChannel(channelId)) {
                is Success -> FindChannelScreenState.Joined(channelId)
                is Failure -> FindChannelScreenState.Error(result.value)
            }
        }
    }

    fun findChannel(channelName: String) {
        val curr = state
        if (curr !is FindChannel.FindChannelIdle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.findChannel(ChannelName(channelName))) {
                is Success -> FindChannelScreenState.Success(listOf(result.value))
                is Failure -> FindChannelScreenState.Error(result.value)
            }
        }
    }

    fun getChannels(offset: UInt?, limit: UInt?) {
        val curr = state
        if (curr !is FindChannel.FindChannelIdle) return
        state = FindChannelScreenState.Loading
        viewModelScope.launch {
            state = when (val result = service.getChannels(offset, limit)) {
                is Success -> FindChannelScreenState.Success(result.value.toList())
                is Failure -> FindChannelScreenState.Error(result.value)
            }
        }
    }

    fun updateSearchChannelInput(searchChannelInput: String) {
        val curr = state
        if (curr is FindChannel.FindChannelIdle) {
            state = curr.updateSearchChannelInput(searchChannelInput)
        }
    }

    fun updatePublicChannels(publicChannels: List<FindChannelItem>) {
        val curr = state
        if (curr is FindChannel.FindChannelIdle) {
            state = curr.updatePublicChannels(publicChannels)
        }
    }

    fun toFindChannel() {
        if (state !is FindChannel) {
            state = FindChannel.FindChannelIdle("", null)
        }
    }
}