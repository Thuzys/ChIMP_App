package com.example.chimp.screens.channel.model

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.coroutines.flow.Flow

/**
 * The response of fetch messages operation.
 */
typealias FetchMessagesResult = Pair<Flow<List<Message>>, Flow<Boolean>>

/**
 * The public contract of the Channel Service.
 *
 * This interface defines the methods that the Channel Service must implement.
 */
interface ChannelService {
    suspend fun fetchMessages() : Either<ResponseError, FetchMessagesResult>
    suspend fun fetchMore(): Either<ResponseError, Unit>
    suspend fun sendMessage(message: Message): Either<ResponseError, Unit>
    suspend fun fetchChannelInfo(): Either<ResponseError, ChannelInfo>
    suspend fun updateChannelInfo(channel: ChannelInfo): Either<ResponseError, Unit>
    suspend fun deleteOrLeaveChannel(): Either<ResponseError, Unit>
    suspend fun fetchAccessControl(): Either<ResponseError, AccessControl>
    suspend fun initSseOnMessages(): Either<ResponseError, Unit>
}