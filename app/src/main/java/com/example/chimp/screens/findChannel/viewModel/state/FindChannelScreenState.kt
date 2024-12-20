package com.example.chimp.screens.findChannel.viewModel.state

import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelInfo
import kotlinx.coroutines.flow.Flow

/**
 * The state of the Find Channel screen.
 */
sealed interface FindChannelScreenState {
    /**
     * The screen is initial.
     */
    data object Initial: FindChannelScreenState
    /**
     * The screen is loading.
     */
    data object Loading : FindChannelScreenState

    /**
     * The user is going back to the registration screen.
     */
    data object BackToRegistration : FindChannelScreenState

    /**
     * The screen has an error.
     *
     * @property error the error that occurred
     * @property goBack the previous screen state
     */
    data class Error(
        val error: ResponseError,
        val goBack: FindChannelScreenState
    ) : FindChannelScreenState

    /**
     * The user is scrolling through the channels.
     */
    sealed class Scrolling : FindChannelScreenState {
        abstract val searchChannelInput: String
        abstract val publicChannels: Flow<List<ChannelBasicInfo>>
        abstract val hasMore: Flow<Boolean>
    }

    /**
     * The screen is showing the normal scrolling state.
     *
     * @property searchChannelInput the search channel input
     * @property publicChannels the public channels
     * @property hasMore whether there are more channels to fetch
     */
    data class NormalScrolling(
        override val publicChannels: Flow<List<ChannelBasicInfo>>,
        override val hasMore: Flow<Boolean>,
        override val searchChannelInput: String = ""
    ) : Scrolling()

    /**
     * The screen is showing the searching scrolling state.
     *
     * @property searchChannelInput the search channel input
     * @property publicChannels the public channels
     * @property hasMore whether there are more channels to fetch
     */
    data class SearchingScrolling(
        override val searchChannelInput: String,
        override val publicChannels: Flow<List<ChannelBasicInfo>>,
        override val hasMore: Flow<Boolean>
    ) : Scrolling()

    /**
     * The screen is showing the information of a channel.
     *
     * @property channel the channel information
     * @property goBack the previous screen state
     */
    data class Info(
        val channel: ChannelInfo,
        val goBack: FindChannelScreenState,
    ) : FindChannelScreenState
}