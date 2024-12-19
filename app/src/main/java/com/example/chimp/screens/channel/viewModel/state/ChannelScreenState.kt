package com.example.chimp.screens.channel.viewModel.state

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.coroutines.flow.Flow

/**
 * The state of the channel screen.
 */
internal interface ChannelScreenState {
    data object Initial : ChannelScreenState
    data object Loading : ChannelScreenState
    data class Scrolling(
        val channel: ChannelBasicInfo,
        val user: UserInfo,
        val accessControl: AccessControl,
        val messages: Flow<List<Message>>,
        val hasMore: Flow<Boolean>,
    ) : ChannelScreenState
    data class Error(
        val error: ResponseError,
        val previous: ChannelScreenState,
    ) : ChannelScreenState
    data class Editing(
        val channel: ChannelInfo,
        val previous: ChannelScreenState,
    ) : ChannelScreenState
}