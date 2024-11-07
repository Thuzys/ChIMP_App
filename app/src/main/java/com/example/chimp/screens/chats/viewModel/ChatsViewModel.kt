package com.example.chimp.screens.chats.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.screens.chats.model.channel.ChatsServices
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val service: ChatsServices,
    private val user: com.example.chimp.screens.chats.model.users.User, //TODO: add user to stateMachine.
): ViewModel() {
    var state: ChatsScreenState by mutableStateOf(ChatsScreenState.Idle)
        private set

    fun toShowChannels() {
        if (state != ChatsScreenState.Loading) {
            state = ChatsScreenState.Loading
            viewModelScope.launch {
                val channels = service.fetchChannels(user)
                TODO("ShowChannels(channels)")
            }
        }
    }
}