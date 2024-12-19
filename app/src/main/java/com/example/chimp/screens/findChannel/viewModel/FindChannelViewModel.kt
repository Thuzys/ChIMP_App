package com.example.chimp.screens.findChannel.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val DEBOUNCE_TIME = 500L

/**
 * ViewModel for the Find Channel screen.
 *
 * This ViewModel is responsible for managing the state of the Find Channel screen.
 *
 * @property service the service used in the ViewModel context.
 */

@OptIn(FlowPreview::class)
class FindChannelViewModel(
    private val service: FindChannelService,
    private val userInfoRepository: UserInfoRepository,
    initialState: FindChannelScreenState = FindChannelScreenState.Init
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _searchText = MutableStateFlow("")
    val state: StateFlow<FindChannelScreenState> = _state

    init {
        viewModelScope.launch {
            _searchText
                .debounce(DEBOUNCE_TIME)
                .map { searchChannelInput -> searchChannelInput }
                .collect { searchChannelInput ->
                    val curr = state.value
                    if (curr !is FindChannelScreenState.Idle) return@collect
                    _state.emit(FindChannelScreenState.Loading)
                    when (val result = service.findChannelsByPartialName(ChannelName(searchChannelInput, ""))) {
                        is Success -> _state.emit(FindChannelScreenState.Idle(_searchText.value, result.value))

                        is Failure -> _state.emit(FindChannelScreenState.Error(
                            ResponseError(
                                result.value.cause,
                                result.value.urlInfo
                            )
                        ))
                    }
                }
        }
    }

    fun joinChannel(channelId: UInt) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Idle) return@launch
            _state.emit(FindChannelScreenState.Loading)
            _state.emit(
                when (val result = service.joinChannel(channelId)) {
                    is Success -> FindChannelScreenState.Init
                    is Failure -> FindChannelScreenState.Error(
                        ResponseError(
                            result.value.cause,
                            result.value.urlInfo
                        )
                    )
                }
            )
        }
    }


    fun getChannels(offset: UInt?, limit: UInt?) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Idle) return@launch
            _state.emit(FindChannelScreenState.Loading)
            when (val result = service.getChannels(offset, limit)) {
                is Success -> _state.emit(FindChannelScreenState.Idle(_searchText.value, result.value))
                is Failure -> _state.emit(FindChannelScreenState.Error(
                    ResponseError(
                        result.value.cause,
                        result.value.urlInfo
                    )
                ))
            }
        }
    }

    fun updateSearchText(searchText: String) {
       viewModelScope.launch {
           val curr = state.value
           if (curr !is FindChannelScreenState.Idle) return@launch
           if (curr.searchChannelInput == searchText) return@launch
           _searchText.emit(searchText)
           _state.emit(curr.copy(searchChannelInput = searchText))
       }
    }

    fun closeError() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Error) return@launch
            _state.emit(FindChannelScreenState.Init)
        }
    }
}