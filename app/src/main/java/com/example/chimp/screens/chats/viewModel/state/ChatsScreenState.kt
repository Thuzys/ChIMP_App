package com.example.chimp.screens.chats.viewModel.state

import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.messages.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface ChatsScreenState {
    data object Initial : ChatsScreenState
    data object Loading : ChatsScreenState
    data class Error(val error: ResponseErrors) : ChatsScreenState
    data class Idle(val channels: List<ChannelBasicInfo>) : ChatsScreenState
    data class ChatSelected(
        val channel: ChannelBasicInfo,
        private val _messages: MutableStateFlow<List<Messages>> = MutableStateFlow(emptyList()),
        val user: User,
    ) : ChatsScreenState {
        val messages: Flow<List<Messages>> = _messages.asStateFlow()
        suspend fun addMessage(message: Messages) {
            val newMessages = _messages.value.toMutableList()
            newMessages.add(message)
            newMessages.sortBy(Messages::time)
            _messages.emit(newMessages)
        }
    }
    data class ChannelInfo(
        val channel: ChannelBasicInfo,
        val goBack: ChatsScreenState,
    ) : ChatsScreenState
}