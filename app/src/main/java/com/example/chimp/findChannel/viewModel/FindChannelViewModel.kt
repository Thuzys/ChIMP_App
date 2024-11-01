package com.example.chimp.findChannel.viewModel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.findChannel.model.FindChannelErrors
import com.example.chimp.findChannel.model.FindChannelItem
import com.example.chimp.findChannel.viewModel.state.FindChannelScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FindChannelViewModel: ViewModel() {
    val scrollState = LazyListState()
    private val items = MutableStateFlow<List<FindChannelItem>>(emptyList())
    val chatItems: StateFlow<List<FindChannelItem>> get() = items

    var state: FindChannelScreenState = FindChannelScreenState.Loading
        private set

    fun updateChatItems(newItems: List<FindChannelItem>) {
        viewModelScope.launch {
            items.value = newItems
        }
    }

    fun joinChannel(channelId: UInt) {
        state = FindChannelScreenState.Joining(channelId)
    }

    fun channelJoined(channelId: UInt) {
        state = FindChannelScreenState.Joined(channelId)
    }

    fun channelJoinError(error: String) {
        state = FindChannelScreenState.Error(FindChannelErrors.JoinError(error))
    }
}