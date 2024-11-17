package com.example.chimp.screens.findChannel.viewModel.state

import com.example.chimp.screens.findChannel.model.FindChannelItem
import com.example.chimp.models.errors.ResponseErrors

/**
 * The state of the Find Channel screen.
 */
sealed interface FindChannelScreenState {
    /**
     * The screen is loading.
     */
    data object Loading : FindChannelScreenState

    /**
     * The screen is successful.
     *
     * @property channels the channels found
     */
    data class Success(val channels: List<FindChannelItem>) : FindChannelScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     */
    data class Error(val error: ResponseErrors) : FindChannelScreenState

    /**
     * The screen has joined a channel.
     *
     * @property channelId the channel id joined
     */
    data class Joined(val channelId: UInt) : FindChannelScreenState
}