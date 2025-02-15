package com.example.chimp.screens.createChannel.model

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the service used in CreateChannelViewModel.
 */
interface CreateChannelService {
    val connection: Flow<Status>
    /**
     * Fetch channels by their partial names.
     * @param channelName the partial name of the channel to find
     * @return an [Either] with the [ChannelInfo] if the channel was found,
     * or a [ResponseError] if it failed.
     */
    suspend fun fetchChannelByNames(
        channelName: String
    ): Either<ResponseError,ChannelInfo>

    /**
     * Create a channel.
     * @param channelInput the input to create the channel
     * @return an [Either] with [Unit] if the channel was created,
     * or a [ResponseError] if it failed.
     */
    suspend fun createChannel(
        channelInput: ChannelInput
    ): Either<ResponseError, ChannelInfo>
}