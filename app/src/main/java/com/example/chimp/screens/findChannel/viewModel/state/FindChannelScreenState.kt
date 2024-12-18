package com.example.chimp.screens.findChannel.viewModel.state

import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.channel.ChannelBasicInfo
import kotlinx.coroutines.flow.Flow

/**
 * The state of the Find Channel screen.
 */
sealed interface FindChannelScreenState {
    /**
     * The screen is initial.
     */
    data object Init: FindChannelScreenState
    /**
     * The screen is loading.
     */
    data object Loading : FindChannelScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(val error: ResponseError) : FindChannelScreenState

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