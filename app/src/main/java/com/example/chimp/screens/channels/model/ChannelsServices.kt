package com.example.chimp.screens.channels.model

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import kotlinx.coroutines.flow.Flow

/**
 * FetchChannelsResult is a typealias for a Pair of two Flows:
 *
 * 1. Flow<ChannelBasicInfo> - a flow of ChannelBasicInfo objects
 * 2. Flow<Boolean> - a flow of boolean values representing whether there are more channels to fetch
 */
typealias FetchChannelsResult = Pair<Flow<List<ChannelInfo>>, Flow<Boolean>>

interface ChannelsServices {
    val connectivity: Flow<Status>
    suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult>
    suspend fun deleteOrLeave(channel: ChannelInfo): Either<ResponseError, Unit>
    suspend fun fetchMore(): Either<ResponseError, Unit>
}