package com.example.chimp.screens.channels.viewModel.state

import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import kotlinx.coroutines.flow.Flow

/**
 * Sealed class that represents the different states of the Channels screen.
 *
 * The states are:
 * - Initial: The initial state of the screen.
 * - Loading: The screen is loading.
 * - BackToRegistration: The user is going back to the registration screen.
 * - Error: An error occurred while loading the screen.
 * - Scrolling: The user is scrolling through the channels.
 * - Info: The user is viewing the information of a channel.
 */
internal sealed interface ChannelsScreenState {
    data object Initial : ChannelsScreenState
    data object Loading : ChannelsScreenState
    data object BackToRegistration : ChannelsScreenState
    data class Error(
        val error: ResponseError,
        val goBack: ChannelsScreenState
    ) : ChannelsScreenState
    data class Scrolling(
        val channels: Flow<List<ChannelInfo>>,
        val hasMore: Flow<Boolean>,
        val connectivity: Flow<Status>
    ) : ChannelsScreenState
    data class Info(
        val channel: ChannelInfo,
        val goBack: ChannelsScreenState,
    ) : ChannelsScreenState
    data class JoiningChannel(
        val invitationCode: String,
        val previous: ChannelsScreenState,
    ) : ChannelsScreenState
}