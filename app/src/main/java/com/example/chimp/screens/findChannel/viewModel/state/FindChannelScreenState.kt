package com.example.chimp.screens.findChannel.viewModel.state

import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import kotlinx.coroutines.flow.Flow

/**
 * The state of the Find Channel screen.
 */
sealed interface FindChannelScreenState {
    /**
     * The screen is loading.
     */
    data object Loading : FindChannelScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(val error: ResponseErrors) : FindChannelScreenState

    /**
     * The screen is idle.
     *
     * @property searchChannelInput the search channel input
     * @property publicChannels the public channels found
     */
    data class Idle(
        val searchChannelInput: String = "",
        val publicChannels: Flow<List<ChannelBasicInfo>>,
    ) : FindChannelScreenState
}