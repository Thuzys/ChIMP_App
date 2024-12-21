package com.example.chimp.screens.createChannel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.Visibility
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.createChannel.model.CreateChannelService
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState.Editing
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState.Loading
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState.NotValidated
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState.Successfull
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState.Validated
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val DEBOUNCE_TIME = 500L

@OptIn(FlowPreview::class)
class CreateChannelViewModel(
    private val service: CreateChannelService,
    private val userInfoRepository: UserInfoRepository,
    initialState: CreateChannelScreenState = Loading
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _channelName = MutableStateFlow("")
    private val _channelVisibility = MutableStateFlow(Visibility.PUBLIC)
    private val _channelAccessControl = MutableStateFlow(AccessControl.READ_ONLY)
    val state: StateFlow<CreateChannelScreenState> = _state
    val user = userInfoRepository.userInfo

    init {
        viewModelScope.launch {
            _channelName
                .debounce(DEBOUNCE_TIME)
                .map { createChannelInput -> createChannelInput }
                .collect { createChannelInput ->
                    val curr = state.value
                    if (curr !is Editing && _channelName == MutableStateFlow("")) return@collect
                    when (service.fetchChannelsByNames(createChannelInput)) {
                        is Success -> _state.emit(NotValidated)

                        is Failure -> _state.emit(Validated(createChannelInput))
                    }
                }
        }
    }



    fun submitChannel(
        channelname: String,
        visibility: Visibility,
        accessControl: AccessControl
    ) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Validated) return@launch
            _state.emit(CreateChannelScreenState.Submit(curr.channelName))
            when (val result = service.createChannel(channelname, visibility, accessControl)) {
                is Success -> _state.emit(Successfull)

                is Failure -> _state.emit(CreateChannelScreenState.Error(result.value))
            }
        }
    }
}