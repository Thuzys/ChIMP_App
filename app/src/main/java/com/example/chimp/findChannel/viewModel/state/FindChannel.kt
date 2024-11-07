package com.example.chimp.findChannel.viewModel.state

import com.example.chimp.findChannel.model.FindChannelItem

/**
 * The state of the Find Channel screen.
 *
 * @property searchChannelInput the search channel input
 * @property publicChannels the public channels found
 */
sealed class FindChannel(
    open val searchChannelInput: String = "",
    open val publicChannels: List<FindChannelItem>? = null,
): FindChannelScreenState {
    abstract fun updateSearchChannelInput(searchChannelInput: String): FindChannel
    abstract fun updatePublicChannels(publicChannels: List<FindChannelItem>): FindChannel

    /**
     * The state representing an idle Find Channel screen.
     *
     * @property searchChannelInput the search channel input
     * @property publicChannels the public channels
     */
    data class FindChannelIdle(
        override val searchChannelInput: String,
        override val publicChannels: List<FindChannelItem>?,
    ) : FindChannel(searchChannelInput, publicChannels) {
        override fun updateSearchChannelInput(searchChannelInput: String): FindChannel {
            return copy(searchChannelInput = searchChannelInput)
        }
        override fun updatePublicChannels(publicChannels: List<FindChannelItem>): FindChannel {
            return copy(publicChannels = publicChannels)
        }
    }
}