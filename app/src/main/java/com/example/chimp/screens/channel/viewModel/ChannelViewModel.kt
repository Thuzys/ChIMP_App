package com.example.chimp.screens.channel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.ChannelService
import com.example.chimp.screens.channel.model.FetchMessagesResult
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Editing
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Error
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Initial
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Loading
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class ChannelViewModel(
    private val channelRepo: ChannelRepository,
    private val service: ChannelService,
    private val userRepo: UserInfoRepository,
    initialState: ChannelScreenState = Initial
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    val channel = channelRepo.channelInfo

    fun loadMessages() {
        viewModelScope.launch { service.initSseOnMessages() }
        viewModelScope.launch {
            val curr = state.value
            val user = userRepo.userInfo.first()
            val channel = channelRepo.channelInfo.first()
            if (curr !is Initial) return@launch
            if (user == null)
                return@launch _state.emit(Error(ResponseError.InternalServerError, curr))
            if (channel == null)
                return@launch _state.emit(Error(ResponseError.InternalServerError, curr))
            val userInfo = UserInfo(user.id, user.name)
            _state.emit(Loading)
            when (val result = service.fetchMessages()) {
                is Success<FetchMessagesResult> -> {
                    channelRepo.saveMessages(result.value.first.first())
                    when (val accessResponse = service.fetchAccessControl()) {
                        is Success -> {
                            _state.emit(
                                Scrolling(
                                    user = userInfo,
                                    channel = channel,
                                    messages = result.value.first,
                                    hasMore = result.value.second,
                                    accessControl = accessResponse.value
                                )
                            )
                        }

                        is Failure<ResponseError> -> {
                            _state.emit(Error(accessResponse.value, curr))
                        }
                    }
                }

                is Failure<ResponseError> -> {
                    _state.emit(Error(result.value, curr))
                }
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            when (val result = service.fetchMore()) {
                is Failure<ResponseError> -> {
                    _state.emit(Error(result.value, curr))
                }

                is Success<Unit> -> return@launch
            }
        }
    }

    fun sendMessage(msg: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            val channel =
                channelRepo.channelInfo.first()
                    ?: return@launch _state.emit(Error(ResponseError.InternalServerError, curr))
            val user =
                userRepo.userInfo.first()
                    ?: return@launch _state.emit(Error(ResponseError.InternalServerError, curr))
            val sendMsg = Message(
                cId = channel.cId,
                message = msg,
                owner = UserInfo(user.id, user.name)
            )
            when (val result = service.sendMessage(sendMsg)) {
                is Success<Unit> -> return@launch
                is Failure<ResponseError> ->
                    _state.emit(Error(result.value, curr))
            }
        }
    }

    fun editChannel(channel: ChannelInfo) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Editing) return@launch
            when (val result = service.updateChannelInfo(channel)) {
                is Success -> _state.emit(curr.previous)
                is Failure<ResponseError> -> _state.emit(Error(result.value, curr))
            }
        }
    }

    fun toEdit() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            when (val result = service.fetchChannelInfo()) {
                is Success<ChannelInfo> -> _state.emit(Editing(result.value, curr))
                is Failure<ResponseError> -> _state.emit(Error(result.value, curr))
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Editing) _state.emit(curr.previous)
            if (curr is Error) _state.emit(curr.previous)
        }
    }

    fun deleteOrLeave(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            when (val result = service.deleteOrLeaveChannel()) {
                is Success -> { onSuccess() }
                is Failure<ResponseError> -> _state.emit(Error(result.value, curr))
            }
        }
    }
}