package com.example.chimp.screens.createChannel.model

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError

/**
 * Interface that defines the service used in CreateChannelViewModel.
 */
interface CreateChannelService {
    /**
     * Fetch channels by their partial names.
     * @param channelName the partial name of the channel to find
     * @return an [Either] with the [ChannelBasicInfo] if the channel was found,
     * or a [ResponseError] if it failed.
     */
    suspend fun fetchChannelByNames(
        channelName: String
    ): Either<ResponseError,ChannelBasicInfo>

    /**
     * Create a channel.
     * @param channelInput the input to create the channel
     * @return an [Either] with [Unit] if the channel was created,
     * or a [ResponseError] if it failed.
     */
    suspend fun createChannel(
        channelInput: ChannelInput
    ): Either<ResponseError, Unit>
}