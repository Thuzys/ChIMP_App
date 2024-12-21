package com.example.chimp.screens.channel.viewModel.state

import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelInvitation
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.coroutines.flow.Flow

/**
 * The state of the channel screen.
 */
internal interface ChannelScreenState {
    data object Initial : ChannelScreenState
    data object Loading : ChannelScreenState
    data object BackToRegistration : ChannelScreenState
    data object BackToChannels : ChannelScreenState
    data class Scrolling(
        val channel: ChannelInfo,
        val user: UserInfo,
        val accessControl: AccessControl,
        val messages: Flow<List<Message>>,
        val hasMore: Flow<Boolean>,
        val connection: Flow<Status>
    ) : ChannelScreenState
    data class Error(
        val error: ResponseError,
        val previous: ChannelScreenState,
    ) : ChannelScreenState
    data class Editing(
        val channel: ChannelInfo,
        val previous: ChannelScreenState,
    ) : ChannelScreenState
    data class Info(
        val channel: ChannelInfo,
        val previous: ChannelScreenState,
    ) : ChannelScreenState
    data class CreatingInvitation(
        val channelInvitation: ChannelInvitation = ChannelInvitation.createDefault(),
        val previous: ChannelScreenState,
    ) : ChannelScreenState
    data class ShowingInvitation(
        val invitation: String,
        val previous: ChannelScreenState,
    ) : ChannelScreenState
}