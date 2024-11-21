package com.example.chimp.services.http

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import com.example.chimp.screens.chats.model.channel.ChannelInfo
import com.example.chimp.screens.chats.model.channel.ChatsServices
import com.example.chimp.screens.chats.model.messages.Messages
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.ws
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

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

    override suspend fun fetchChannelMessages(channel: ChannelBasicInfo):
            Either<ResponseErrors, MutableStateFlow<List<Messages>>> {
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

    override suspend fun fetchSSLMessages(channel: ChannelBasicInfo):
            Either<ResponseErrors, Flow<Messages>>
    {
        TODO()
    }
}