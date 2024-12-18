package com.example.chimp.screens.channels.service

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.screens.channels.model.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channels.model.channel.ChannelsServices
import com.example.chimp.screens.channels.model.channel.FetchChannelsResult
import com.example.chimp.screens.chats.model.messages.Messages
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

internal class FakeService: ChannelsServices {
    private val controller = Channel<Unit>()
    private val channelName = ChannelName("test")
    private val user = UserInfo(1u, "test")
    private val flow = MutableStateFlow(emptyList<ChannelBasicInfo>())
    suspend fun unlock() = controller.send(Unit)
    override suspend fun fetchChannels(): Either<ResponseErrors, FetchChannelsResult> {
        controller.receive()
        return success(Pair(flow, flowOf(true)))
    }

    override suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseErrors, Unit> {
        controller.receive()
        return success(Unit)
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo> {
        controller.receive()
        return success(ChannelInfo(1u, channelName, owner = user))
    }

    override suspend fun fetchMore(): Either<ResponseErrors, Unit> {
        controller.receive()
        flow.emit(flow.value + ChannelBasicInfo(1u, channelName))
        return success(Unit)
    }

}