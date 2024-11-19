package com.example.chimp.services.http

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelInfo
import com.example.chimp.screens.chats.model.channel.ChatsServices
import com.example.chimp.screens.chats.model.messages.Messages
import io.ktor.client.HttpClient
import io.ktor.client.plugins.sse.sse
import kotlinx.coroutines.flow.Flow

class ChIMPChatsAPI(private val client: HttpClient): ChatsServices {
    override suspend fun fetchChannels(user: User): Either<ResponseErrors, List<ChannelBasicInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChannel(channel: ChannelBasicInfo): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelMessages(channel: ChannelBasicInfo): Either<ResponseErrors, List<Messages>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(
        channel: ChannelBasicInfo,
        message: String
    ): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    private fun connectToChannel(channel: ChannelBasicInfo) {
        TODO()
    }

    private fun parseMessage(message: String): Messages {
        TODO()
    }

    override suspend fun fetchSseMessages(user: User): Either<ResponseErrors, Flow<Messages>> {
        TODO()
    }
}