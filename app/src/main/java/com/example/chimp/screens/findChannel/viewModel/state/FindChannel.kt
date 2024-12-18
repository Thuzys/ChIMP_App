package com.example.chimp.screens.findChannel.viewModel.state

import com.example.chimp.screens.findChannel.model.FindChannelItem
import kotlinx.coroutines.flow.Flow

/**
 * The state of the Find Channel screen.
 *
 * @property publicChannels the public channels found
 */
sealed class FindChannel(
    open val publicChannels: Flow<List<FindChannelItem>>? = null,
): FindChannelScreenState {
    abstract fun updatePublicChannels(publicChannels: Flow<List<FindChannelItem>>?): FindChannel

    /**
     * The state representing an idle Find Channel screen.
     *
     * @property publicChannels the public channels
     */
    data class FindChannelIdle(
        override val publicChannels: Flow<List<FindChannelItem>>?,
    ) : FindChannel(publicChannels) {
        override fun updatePublicChannels(publicChannels: Flow<List<FindChannelItem>>?): FindChannel {
            return copy(publicChannels = publicChannels)
        }
    }

}