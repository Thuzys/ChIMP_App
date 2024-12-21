package com.example.chimp.screens.channels.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.BackToRegistration
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Error
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Info
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Scrolling
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.screens.channels.model.FetchChannelsResult
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.CreateUserInvitation
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Initial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

internal class ChannelsViewModel(
    private val service: ChannelsServices,
    private val userInfoRepository: UserInfoRepository,
    private val channelRepository: ChannelRepository,
    initialState: ChannelsScreenState = Initial,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    val user = userInfoRepository.userInfo

    fun loadChannels() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Initial) return@launch
            _state.emit(ChannelsScreenState.Loading)
            when (val result = service.fetchChannels()) {
                is Success<FetchChannelsResult> -> {
                    userInfoRepository.updateChannelList(result.value.first.first())
                    _state.emit(
                        Scrolling(result.value.first, result.value.second, service.connectivity)
                    )
                }

                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> {
                            userInfoRepository.clearUserInfo()
                            _state.emit(BackToRegistration)
                        }

                        ResponseError.NoInternet -> {
                            _state.emit(
                                Scrolling(
                                    userInfoRepository.channelList,
                                    flowOf(false),
                                    service.connectivity
                                )
                            )
                        }

                        else -> _state.emit(Error(result.value, curr))
                    }
                }
            }
        }
    }

    fun backToRegistration() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is BackToRegistration) return@launch
            userInfoRepository.clearUserInfo()
        }
    }

    fun onChannelClick(channel: ChannelInfo, navigateToChannel: () -> Unit) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            channelRepository.updateChannelInfo(channel)
            navigateToChannel()
        }
    }

    fun deleteOrLeave(channel: ChannelInfo) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            _state.emit(ChannelsScreenState.Loading)
            when (val result = service.deleteOrLeave(channel)) {
                is Success<Unit> -> _state.emit(curr)

                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> {
                            userInfoRepository.clearUserInfo()
                            _state.emit(BackToRegistration)
                        }

                        ResponseError.NoInternet -> {
                            _state.emit(Error(result.value, Initial))
                        }

                        else -> _state.emit(Error(result.value, curr))
                    }
                }
            }
        }
    }

    fun toChannelInfo(channel: ChannelInfo) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            _state.emit(Info(channel, curr))
        }
    }

    fun goBack() {
        viewModelScope.launch {
            when (val curr = state.value) {
                is Info -> _state.emit(curr.goBack)
                is CreateUserInvitation -> _state.emit(curr.goBack)
                is Error -> _state.emit(curr.goBack)
                else -> return@launch
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
                        ResponseError.Unauthorized -> {
                            userInfoRepository.clearUserInfo()
                            _state.emit(BackToRegistration)
                        }

                        ResponseError.NoInternet -> {
                            _state.emit(
                                Scrolling(
                                    userInfoRepository.channelList,
                                    flowOf(false),
                                    service.connectivity
                                )
                            )
                        }

                        else -> _state.emit(Error(result.value, curr))
                    }
                }

                is Success -> return@launch
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is BackToRegistration) return@launch
            _state.emit(BackToRegistration)
            userInfoRepository.clearUserInfo()
        }
    }

    fun reset() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Initial) return@launch
            _state.emit(Initial)
        }
    }

    fun toUserInvitation(date: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            when(val result = service.createUserInvitation(date)) {
                is Failure<ResponseError> -> {
                    when (result.value) {
                        ResponseError.Unauthorized -> {
                            userInfoRepository.clearUserInfo()
                            _state.emit(BackToRegistration)
                        }

                        ResponseError.NoInternet -> {
                            _state.emit(Error(result.value, Initial))
                        }

                        else -> _state.emit(Error(result.value, curr))
                    }
                }

                is Success<String> -> _state.emit(CreateUserInvitation(result.value, curr))
            }
        }
    }

}