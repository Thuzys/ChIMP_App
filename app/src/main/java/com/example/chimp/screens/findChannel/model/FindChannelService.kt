package com.example.chimp.screens.findChannel.model

import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the service used in FindChannelViewModel.
 */
interface FindChannelService {
    /**
     * Join a channel.
     * @param channelId the id of the channel to join
     * @return an [Either] with [Unit] if the channel was joined,
     * or a [ResponseErrors] if it failed.
     */
    suspend fun joinChannel(channelId: UInt, invitationCode: String?): Either<ResponseErrors, Unit>

    /**
     * Find a channel by its name.
     *
     * @param channelName the name of the channel to find
     * @return an [Either] with the [ChannelBasicInfo] if the channel was found,
     * or a [ResponseErrors] if it failed.
     */
    suspend fun findChannelByName(channelName: ChannelName): Either<ResponseErrors, ChannelBasicInfo>

    /**
     * Find channels by their partial names.
     *
     * @param channelName the partial name of the channel to find
     * @return an [Either] with a [Flow] of [ChannelBasicInfo] if the channels were found,
     */
    suspend fun findChannelsByPartialName(
        channelName: ChannelName,
    ): Either<ResponseErrors, Flow<List<ChannelBasicInfo>>>

    /**
     * Get a list of channels.
     *
     * @param offset the offset to start fetching channels from
     * @param limit the maximum number of channels to fetch
     * @return an [Either] with a [Flow] of [ChannelBasicInfo] if the channels were found,
     * or a [ResponseErrors] if it failed.
     */
    suspend fun getChannels(
        offset: UInt?,
        limit: UInt?,
    ): Either<ResponseErrors, Flow<List<ChannelBasicInfo>>>
}