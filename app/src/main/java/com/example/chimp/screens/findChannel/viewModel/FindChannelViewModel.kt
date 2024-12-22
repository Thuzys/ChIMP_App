package com.example.chimp.screens.findChannel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.screens.findChannel.model.FindChannelsResult
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState.BackToRegistration
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState.Info
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
    private val channelRepository: ChannelRepository,
    initialState: FindChannelScreenState = FindChannelScreenState.Initial
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _searchText = MutableStateFlow("")
    private val connection = service.connectivity
    val state: StateFlow<FindChannelScreenState> = _state
    val user = userInfoRepository.userInfo

    init {
        viewModelScope.launch {
            _searchText
                .debounce(DEBOUNCE_TIME)
                .map { searchChannelInput -> searchChannelInput }
                .collect { searchChannelInput ->
                    val curr = state.value
                    if (curr !is FindChannelScreenState.SearchingScrolling) return@collect
                    when (val result = service.getChannels(searchChannelInput)) {
                        is Success -> _state.emit(
                            FindChannelScreenState.SearchingScrolling(
                                searchChannelInput,
                                result.value.first,
                                result.value.second
                            )
                        )

                        is Failure -> _state.emit(
                            if (result.value == ResponseError.Unauthorized) BackToRegistration
                            else FindChannelScreenState.Error(result.value, curr)
                        )
                    }
                }
        }
        viewModelScope.launch {
            connection.collect { status ->
                if (status == ConnectivityObserver.Status.DISCONNECTED) {
                    _state.value = FindChannelScreenState.Error(
                        ResponseError.NoInternet,
                        _state.value
                    )
                }
            }
        }
    }

    fun joinChannel(
        channelId: UInt,
        navigateToChannel: () -> Unit
    ) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Scrolling) return@launch
            _state.emit(FindChannelScreenState.Loading)
            when (val result = service.joinChannel(channelId)) {
                is Success -> {
                    channelRepository.updateChannelInfo(result.value)
                    navigateToChannel()
                    _state.emit(curr)
                }

                is Failure -> _state.emit(
                    if (result.value == ResponseError.Unauthorized) BackToRegistration
                    else FindChannelScreenState.Error(result.value, curr)
                )
            }
        }
    }

    fun getChannels() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Initial) return@launch
            _state.emit(FindChannelScreenState.Loading)
            when (val result = service.getChannels()) {
                is Success<FindChannelsResult> -> _state.emit(
                    FindChannelScreenState.NormalScrolling(
                        result.value.first,
                        result.value.second,
                        _searchText.value
                    ))

                is Failure<ResponseError> -> _state.emit(
                    if (result.value == ResponseError.Unauthorized) BackToRegistration
                    else FindChannelScreenState.Error(result.value, curr)
                )
            }
        }
    }

    fun getChannels(name: String) {
        viewModelScope.launch {
            if (name.isBlank()) return@launch
            val curr = state.value
            if (curr !is FindChannelScreenState.Initial) return@launch
            _state.emit(FindChannelScreenState.Loading)
            when (val result = service.getChannels(name)) {
                is Success<FindChannelsResult> -> _state.emit(
                    FindChannelScreenState.SearchingScrolling(
                        name,
                        result.value.first,
                        result.value.second
                    ))

                is Failure<ResponseError> -> _state.emit(
                    if (result.value == ResponseError.Unauthorized) BackToRegistration
                    else FindChannelScreenState.Error(result.value, curr)
                )

            }
        }
    }

    fun updateSearchText(searchText: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Scrolling) return@launch
            if (searchText.isBlank()) {
                reset()
                return@launch
            }
            _searchText.emit(searchText)
            _state.emit(
                FindChannelScreenState.SearchingScrolling(
                    searchText,
                    curr.publicChannels,
                    curr.hasMore
                )
            )
        }
    }

    fun toChannelInfo(channel: ChannelInfo) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.Scrolling) return@launch
            _state.emit(Info(channel, curr))
        }
    }

    fun goBack() {
        viewModelScope.launch {
            when (val curr = state.value) {
                is Info -> _state.emit(curr.goBack)
                is FindChannelScreenState.Error -> _state.emit(curr.goBack)
                else -> return@launch
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is FindChannelScreenState.NormalScrolling) return@launch
            when (val result = service.fetchMore()) {
                is Success -> return@launch

                is Failure<ResponseError> ->
                    if (result.value == ResponseError.Unauthorized) _state.emit(BackToRegistration)
                    else _state.emit(FindChannelScreenState.Error(result.value, curr))
            }
        }
    }

    fun loadMore(name: String) {
        viewModelScope.launch {
            if (name.isBlank()) return@launch
            val curr = state.value
            if (curr !is FindChannelScreenState.SearchingScrolling) return@launch
            when (val result = service.fetchMore(name)) {
                is Success -> return@launch

                is Failure<ResponseError> ->
                    if (result.value == ResponseError.Unauthorized) _state.emit(BackToRegistration)
                    else _state.emit(FindChannelScreenState.Error(result.value, curr))
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
            if (curr is FindChannelScreenState.Initial) return@launch
            _searchText.emit("")
            _state.emit(FindChannelScreenState.Initial)
        }
    }
}