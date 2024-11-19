package com.example.chimp.screens.chats.model.channel

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.model.messages.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ChatsServices {
    suspend fun fetchChannels(user: User): Either<ResponseErrors, List<ChannelBasicInfo>>
    suspend fun deleteChannel(channel: ChannelBasicInfo): Either<ResponseErrors, Unit>
    suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo>
    suspend fun fetchChannelMessages(channel: ChannelBasicInfo): Either<ResponseErrors, MutableStateFlow<List<Messages>>>
    suspend fun sendMessage(channel: ChannelBasicInfo, message: String): Either<ResponseErrors, Unit>
    suspend fun fetchSSLMessages(channel: ChannelBasicInfo): Either<ResponseErrors, Flow<Messages>>
}