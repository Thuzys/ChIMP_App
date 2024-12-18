package com.example.chimp.screens.channels.model.channel

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import kotlinx.coroutines.flow.Flow

/**
 * FetchChannelsResult is a typealias for a Pair of two Flows:
 *
 * 1. Flow<ChannelBasicInfo> - a flow of ChannelBasicInfo objects
 * 2. Flow<Boolean> - a flow of boolean values representing whether there are more channels to fetch
 */
typealias FetchChannelsResult = Pair<Flow<List<ChannelBasicInfo>>, Flow<Boolean>>

interface ChannelsServices {
    suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult>
    suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseError, Unit>
    suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo>
    suspend fun fetchMore(): Either<ResponseError, Unit>
}