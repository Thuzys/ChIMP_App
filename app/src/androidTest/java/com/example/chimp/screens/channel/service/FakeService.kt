package com.example.chimp.screens.channel.service

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelInvitation
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.ChannelService
import com.example.chimp.screens.channel.model.FetchMessagesResult
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

internal class FakeService: ChannelService {
    private val controller = Channel<Unit>()
    private val flow = MutableStateFlow(emptyList<Message>())
    private val channel = ChannelInfo(
        1u,
        ChannelName("test", "test"),
        owner = UserInfo(1u, "test"),
    )

    suspend fun unlock() = controller.send(Unit)

    override suspend fun fetchMessages(): Either<ResponseError, FetchMessagesResult> {
        controller.receive()
        return success(Pair(flow, flowOf(true)))
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun sendMessage(message: Message): Either<ResponseError, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun fetchChannelInfo(): Either<ResponseError, ChannelInfo> {
        controller.receive()
        return success(channel)
    }

    override suspend fun updateChannelInfo(channel: ChannelInfo): Either<ResponseError, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun deleteOrLeaveChannel(): Either<ResponseError, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun fetchAccessControl(): Either<ResponseError, AccessControl> {
        controller.receive()
        return success(READ_WRITE)
    }

    override suspend fun initSseOnMessages() {
        TODO("Not yet implemented")
    }

    override suspend fun createChannelInvitation(channelInvitation: ChannelInvitation): Either<ResponseError, String> {
        TODO("Not yet implemented")
    }

}