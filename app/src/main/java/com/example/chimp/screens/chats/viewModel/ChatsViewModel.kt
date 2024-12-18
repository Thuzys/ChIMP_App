package com.example.chimp.screens.chats.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChatsServices
import com.example.chimp.screens.chats.model.messages.Messages
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState.ChannelInfo
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState.ChatSelected
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState.Idle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val service: ChatsServices,
    initialState: ChatsScreenState = ChatsScreenState.Initial,
    private val user: User,
): ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun init() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is ChatsScreenState.Initial) return@launch
            _state.emit(ChatsScreenState.Loading)
            //TODO: Add dataStore to save user data
            when(val result = service.fetchChannels(user)) {
                is Success<List<ChannelBasicInfo>> -> _state.emit(Idle(result.value))
                is Failure<ResponseErrors> -> _state.emit(ChatsScreenState.Error(result.value))
            }
        }
    }

    fun toChannelInfo(channel: ChannelBasicInfo) {
        viewModelScope.launch {
            if (state.value is ChannelInfo) return@launch
            _state.emit(ChannelInfo(channel, state.value))
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            TODO()
        }
    }

    fun toIdle() {
        viewModelScope.launch {
            if (state.value is Idle) return@launch
            _state.emit(ChatsScreenState.Loading)
            when(val result = service.fetchChannels(user)) {
                is Success<List<ChannelBasicInfo>> -> _state.emit(Idle(result.value))
                is Failure<ResponseErrors> -> _state.emit(ChatsScreenState.Error(result.value))
            }
        }
    }

    fun deleteChannel(channel: ChannelBasicInfo) {
        viewModelScope.launch {
            TODO()
        }
    }

    fun sseMessageLoader() {
        viewModelScope.launch {
            val curr = state.value
            while (curr is ChatSelected) {
                when(val result = service.fetchSSLMessages(curr.channel)) {
                    is Success<Flow<Messages>> -> {
                        result
                            .value
                            .collect {
                                curr.addMessage(it)
                            }
                    }
                    is Failure<ResponseErrors> -> _state.emit(ChatsScreenState.Error(result.value))
                }
            }
        }
    }

    fun toChatInfo(channel: ChannelBasicInfo) {
        viewModelScope.launch {
            if (state.value is ChannelInfo) return@launch
            _state.emit(ChannelInfo(channel, state.value))
        }
    }

    fun outChatInfo() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is ChannelInfo) return@launch
            _state.emit(curr.goBack)
        }
    }

    fun toChatSelected(channel: ChannelBasicInfo) {
        viewModelScope.launch {
            if (state.value is ChatSelected) return@launch
            _state.emit(ChatsScreenState.Loading)
            when(val result =service.fetchChannelMessages(channel)){
                is Success -> _state.emit(ChatSelected(channel, result.value, user))
                is Failure -> _state.emit(ChatsScreenState.Error(result.value))
            }
        }
    }
}