package com.example.chimp.screens.findChannel.model

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import kotlinx.coroutines.flow.Flow

typealias FindChannelsResult = Pair<Flow<List<ChannelInfo>>, Flow<Boolean>>

/**
 * Interface that defines the service used in FindChannelViewModel.
 */
interface FindChannelService {
    /**
     * The flow of the current user's connectivity status.
     */
    val connectivity: Flow<Status>
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
     * @return an [Either] with a [Flow] of [ChannelInfo] if the channels were found,
     */
    suspend fun getChannels(name: String, ): Either<ResponseError, FindChannelsResult>

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

}