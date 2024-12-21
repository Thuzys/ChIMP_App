package com.example.chimp.screens.createChannel.model

import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.Visibility
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import kotlinx.coroutines.flow.Flow

typealias FetchChannelsByNamesResult = Pair<Flow<List<ChannelBasicInfo>>, Flow<Boolean>>

/**
 * Interface that defines the service used in CreateChannelViewModel.
 */
interface CreateChannelService {
    /**
     * Fetch channels by their partial names.
     * @param channelName the partial name of the channel to find
     * @return an [Either] with a [List] of [ChannelBasicInfo] if the channels were found,
     * or a [ResponseError] if it failed.
     */
    suspend fun fetchChannelsByNames(
        channelName: String
    ): Either<ResponseError,List<ChannelBasicInfo>>

    /**
     * Create a channel.
     * @param channelName the name of the channel to create
     * @param visibility the visibility of the channel
     * @param accessControl the access control of the channel
     * @return an [Either] with [Unit] if the channel was created,
     * or a [ResponseError] if it failed.
     */
    suspend fun createChannel(
        channelName: String,
        visibility: Visibility,
        accessControl: AccessControl
    ): Either<ResponseError, Unit>
}