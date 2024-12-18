package com.example.chimp.screens.channels.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.channels.model.channel.ChannelsServices
import com.example.chimp.screens.channels.model.channel.FetchChannelsResult
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.BackToRegistration
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Error
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Info
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Scrolling
import com.example.chimp.screens.channels.model.channel.ChannelInfo
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Initial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChannelsViewModel(
    private val service: ChannelsServices,
    private val userInfoRepository: UserInfoRepository,
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
                is Success<FetchChannelsResult> -> _state.emit(
                    Scrolling(result.value.first, result.value.second)
                )

                is Failure<ResponseError> -> _state.emit(
                    Error(result.value, curr)
                )
            }
        }
    }

    fun toChannelInfo(channel: ChannelBasicInfo) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Scrolling) return@launch
            _state.emit(ChannelsScreenState.Loading)
            when (val result = service.fetchChannelInfo(channel)) {
                is Success<ChannelInfo> -> _state.emit(Info(result.value, curr))
                is Failure<ResponseError> -> _state.emit(
                    Error(result.value, curr)
                )
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            when (val curr = state.value) {
                is Info -> _state.emit(curr.goBack)
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
                is Failure<ResponseError> -> _state.emit(Error(result.value, curr))

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

}