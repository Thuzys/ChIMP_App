package com.example.chimp.screens.createChannel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chimp.models.DataInput
import com.example.chimp.models.either.Failure
import com.example.chimp.models.either.Success
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.createChannel.model.ChannelInput
import com.example.chimp.screens.createChannel.model.CreateChannelService
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState
import com.example.chimp.screens.createChannel.viewModel.state.CreateChannelScreenState.Editing
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
    private val channelRepository: ChannelRepository,
    initialState: CreateChannelScreenState = Editing()
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    private val _channelName = MutableStateFlow("")
    val state: StateFlow<CreateChannelScreenState> = _state

    init {
        viewModelScope.launch {
            _channelName
                .debounce(DEBOUNCE_TIME)
                .map { channelName -> channelName }
                .collect { channelName ->
                    val curr = state.value
                    if (curr !is Editing || _channelName.value.isBlank()) return@collect
                    when (service.fetchChannelByNames(channelName)) {
                        is Success -> {

                            if(_channelName.value == curr.channelName.input){
                                _state.emit(Editing(DataInput(channelName, failure("Channel Name Exists"))))
                            }
                    }
                        is Failure -> {
                            if(_channelName.value == curr.channelName.input) {
                                _state.emit(Editing(DataInput(channelName, success(true))))
                            }
                        }
                    }
                }
        }
    }

    fun updateChannelName(channelName: String) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Editing) return@launch
            _channelName.emit(channelName)
            _state.emit(Editing(DataInput(channelName, curr.channelName.validation)))
        }
    }

    fun submitChannel(
        channelInput: ChannelInput,
        navigateToChannel: () -> Unit
    ) {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is Editing) return@launch
            _state.emit(CreateChannelScreenState.Submit)
            when (val result = service.createChannel(channelInput)) {
                is Success -> {
                    channelRepository.updateChannelInfo(result.value)
                    navigateToChannel()
                    _state.emit(Editing())
                }

                is Failure -> _state.emit(CreateChannelScreenState.Error(result.value, curr))
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            val curr = state.value
            if (curr !is CreateChannelScreenState.Error) return@launch
            _state.emit(curr.goBack)
        }
    }

    fun reset() {
        viewModelScope.launch {
            val curr = state.value
            if (curr is Editing) return@launch
            _channelName.emit("")
            _state.emit(Editing())
        }
    }

}