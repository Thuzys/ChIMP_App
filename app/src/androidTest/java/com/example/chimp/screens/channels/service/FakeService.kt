package com.example.chimp.screens.channels.service

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.channels.model.FetchChannelsResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

internal class FakeService : ChannelsServices {
    private val controller = Channel<Unit>()
    override val connectivity: Flow<Status>
        get() = flowOf(Status.CONNECTED)

    override suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult> {
        controller.receive()
        return success(FetchChannelsResult(MutableStateFlow(emptyList()), flowOf(true)))
    }

    override suspend fun deleteOrLeave(channel: ChannelInfo): Either<ResponseError, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun createUserInvitation(date: String): Either<ResponseError, String> {
        controller.receive()
        return success("test")
    }

    override suspend fun joinChannel(invitationCode: String): Either<ResponseError, ChannelInfo> {
        controller.receive()
        return success(
            ChannelInfo(
                cId = 1u,
                name = ChannelName("test", "test"),
                owner = UserInfo(1u, "test")
            )
        )
    }

    suspend fun unlock() {
        controller.send(Unit)
    }

}