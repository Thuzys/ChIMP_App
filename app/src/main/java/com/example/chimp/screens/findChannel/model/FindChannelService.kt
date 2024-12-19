package com.example.chimp.screens.findChannel.model

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import kotlinx.coroutines.flow.Flow

typealias FindChannelsResult = Pair<Flow<List<ChannelBasicInfo>>, Flow<Boolean>>

/**
 * Interface that defines the service used in FindChannelViewModel.
 */
interface FindChannelService {
    /**
     * Join a channel.
     * @param channelId the id of the channel to join
     * @return an [Either] with [Unit] if the channel was joined,
     * or a [ResponseError] if it failed.
     */
    suspend fun joinChannel(channelId: UInt): Either<ResponseError, ChannelInfo>

    /**
     * Find channels by their partial names.
     *
     * @param name the partial name of the channel to find
     * @return an [Either] with a [Flow] of [ChannelBasicInfo] if the channels were found,
     */
    suspend fun getChannels(
        name: String,
    ): Either<ResponseError, Unit>

    /**
     * Get a list of public channels.
     */
    suspend fun getChannels(): Either<ResponseError, FindChannelsResult>

    /**
     * Fetch more public channels.
     */
    suspend fun fetchMore(): Either<ResponseError, Unit>

    /**
     * Fetch more public channels.
     */
    suspend fun fetchMore(name: String): Either<ResponseError, Unit>

    /**
     * Fetch the information of a channel.
     * @param channel the channel to fetch the information of
     * @return an [Either] with the [ChannelBasicInfo] if the channel was found,
     * or a [ResponseError] if it failed.
     */
    suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo>
}