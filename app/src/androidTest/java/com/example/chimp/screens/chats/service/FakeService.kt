package com.example.chimp.screens.chats.service

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelInfo
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.chats.model.channel.ChatsServices
import com.example.chimp.screens.chats.model.messages.Messages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class FakeService: ChatsServices {
    private val channels = mutableListOf(
        ChannelBasicInfo(1u, ChannelName("Channel 1")),
        ChannelBasicInfo(2u, ChannelName("Channel 2")),
        ChannelBasicInfo(3u, ChannelName("Channel 3")),
        ChannelBasicInfo(4u, ChannelName("Channel 4")),
    )
    override suspend fun fetchChannels(user: User): Either<ResponseErrors, List<ChannelBasicInfo>> {
        return success(channels)
    }

    override suspend fun deleteChannel(channel: ChannelBasicInfo): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelMessages(channel: ChannelBasicInfo): Either<ResponseErrors, MutableStateFlow<List<Messages>>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(
        channel: ChannelBasicInfo,
        message: String
    ): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSSLMessages(channel: ChannelBasicInfo): Either<ResponseErrors, Flow<Messages>> {
        TODO("Not yet implemented")
    }
}