package com.example.chimp.screens.channel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelInvitation
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.ChannelService
import com.example.chimp.screens.channel.model.FetchMessagesResult
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_ONLY
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.BackToChannels
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.BackToRegistration
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.CreatingInvitation
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Editing
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Error
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Info
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Initial
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Loading
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.ShowingInvitation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
        viewModelScope.launch {
            val curr = state.value
            val user = userRepo.userInfo.first()
            val channel = channelRepo.channelInfo.first()
            if (curr !is Initial) return@launch
            if (user == null)
                return@launch _state.emit(Error(ResponseError.NoUserContext, BackToRegistration))
            if (channel == null)
                return@launch _state.emit(Error(ResponseError.NoChannelContext, BackToChannels))
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
                                    accessControl = accessResponse.value,
                                    connection = service.connectivity
                                )
                            )
                        }

                        is Failure<ResponseError> -> {
                            when (accessResponse.value) {
                                ResponseError.Unauthorized -> _state.emit(
                                    Error(
                                        accessResponse.value,
                                        BackToRegistration
                                    )
                                )

                                ResponseError.NoInternet -> _state.emit(
                                    Error(
                                        accessResponse.value,
                                        Scrolling(
                                            user = userInfo,
                                            channel = channel,
                                            accessControl = READ_ONLY,
                                            hasMore = flowOf(false),
                                            messages = result.value.first,
                                            connection = service.connectivity
                                        )
                                    )
                                )

                                else -> _state.emit(Error(accessResponse.value, BackToChannels))
                            }
                        }
                    }
                }

                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> _state.emit(
                            Error(
                                result.value,
                                BackToRegistration
                            )
                        )

                        ResponseError.NoInternet -> _state.emit(
                            Error(
                                result.value,
                                Scrolling(
                                    user = userInfo,
                                    channel = channel,
                                    accessControl = READ_ONLY,
                                    hasMore = flowOf(false),
                                    messages = flowOf(channelRepo.fetchChannelMessages(channel)),
                                    connection = service.connectivity
                                )
                            )
                        )

                        else -> _state.emit(Error(result.value, BackToChannels))
                    }
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
                    when (result.value) {
                        ResponseError.Unauthorized -> _state.emit(
                            Error(
                                ResponseError.Unauthorized,
                                BackToRegistration
                            )
                        )

                        else -> _state.emit(Error(result.value, curr))
                    }
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
                    ?: return@launch _state.emit(
                        Error(
                            ResponseError.NoChannelContext,
                            BackToChannels
                        )
                    )
            val user =
                userRepo.userInfo.first()
                    ?: return@launch _state.emit(
                        Error(
                            ResponseError.NoUserContext,
                            BackToRegistration
                        )
                    )
            val sendMsg = Message(
                cId = channel.cId,
                message = msg,
                owner = UserInfo(user.id, user.name)
            )
            when (val result = service.sendMessage(sendMsg)) {
                is Success<Unit> -> return@launch
                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> _state.emit(
                            Error(
                                result.value,
                                BackToRegistration
                            )
                        )

                        ResponseError.NoInternet -> _state.emit(
                            Error(
                                result.value,
                                curr.copy(accessControl = READ_ONLY, hasMore = flowOf(false))
                            )
                        )

                        else -> _state.emit(Error(result.value, curr))
                    }
                }
            }
        }
    }

    fun editChannel(channel: ChannelInfo) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Editing) return@launch
            _state.emit(Loading)
            when (val result = service.updateChannelInfo(channel)) {
                is Success -> {
                    channelRepo.updateChannelInfo(channel)
                    if (curr.previous is Scrolling) _state.emit(curr.previous.copy(channel = channel))
                    else _state.emit(curr.previous)
                }

                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> _state.emit(
                            Error(
                                result.value,
                                BackToRegistration
                            )
                        )

                        else -> _state.emit(Error(result.value, curr))
                    }
                }
            }
        }
    }

    fun toEdit() {
        viewModelScope.launch {
            val curr = state.value
            val channel = channelRepo.channelInfo.first() ?: return@launch
            if (curr !is Scrolling) return@launch
            _state.emit(Editing(channel, curr))
        }
    }

    fun toCreateInvitation() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            _state.emit(CreatingInvitation(ChannelInvitation.createDefault(), curr))
        }
    }

    fun goBack() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Editing) _state.emit(curr.previous)
            if (curr is Error) _state.emit(curr.previous)
            if (curr is Info) _state.emit(curr.previous)
            if (curr is CreatingInvitation) _state.emit(curr.previous)
            if (curr is ShowingInvitation) _state.emit(curr.previous)
        }
    }

    fun deleteOrLeave(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            when (val result = service.deleteOrLeaveChannel()) {
                is Success -> {
                    onSuccess()
                }

                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> _state.emit(
                            Error(
                                result.value,
                                BackToRegistration
                            )
                        )

                        else -> _state.emit(Error(result.value, curr))
                    }
                }
            }
        }
    }

    fun toInfo() {
        viewModelScope.launch {
            val curr = state.value
            val channel = channelRepo.channelInfo.first() ?: return@launch
            if (curr !is Scrolling) return@launch
            _state.emit(Info(channel, curr))
        }
    }

    fun generateInvitation(channelInvitation: ChannelInvitation) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is CreatingInvitation) return@launch
            when (val result = service.createChannelInvitation(channelInvitation)) {
                is Success -> _state.emit(ShowingInvitation(result.value, curr.previous))
                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> _state.emit(
                            Error(
                                result.value,
                                BackToRegistration
                            )
                        )

                        else -> _state.emit(Error(result.value, curr))
                    }
                }
            }
        }
    }
}
