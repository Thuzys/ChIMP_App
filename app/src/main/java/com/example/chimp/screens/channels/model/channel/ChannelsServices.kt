package com.example.chimp.screens.channels.model.channel

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors
import kotlinx.coroutines.flow.Flow

/**
 * FetchChannelsResult is a typealias for a Pair of two Flows:
 *
 * 1. Flow<ChannelBasicInfo> - a flow of ChannelBasicInfo objects
 * 2. Flow<Boolean> - a flow of boolean values representing whether there are more channels to fetch
 */
typealias FetchChannelsResult = Pair<Flow<List<ChannelBasicInfo>>, Flow<Boolean>>

interface ChannelsServices {
    suspend fun fetchChannels(): Either<ResponseErrors, FetchChannelsResult>
    suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseErrors, Unit>
    suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo>
    suspend fun fetchMore(): Either<ResponseErrors, Unit>
}