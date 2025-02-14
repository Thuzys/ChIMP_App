package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.screens.findChannel.model.FindChannelsResult
import kotlinx.coroutines.flow.Flow

class DummyFindChannelService: FindChannelService {

    private val channels = mutableListOf(
        ChannelInfo(
            cId = 1u,
            name = ChannelName("Channel 1", "Channel 1"),
            owner = UserInfo(1u, "Owner 1"),
        ),
        ChannelInfo(
            cId = 2u,
            name = ChannelName("Channel 2", "Channel 2"),
            owner = UserInfo(2u, "Owner 2"),
        ),
        ChannelInfo(
            cId = 3u,
            name = ChannelName("Channel 3", "Channel 3"),
            owner = UserInfo(3u, "Owner 3"),
        ),
    )
    override val connectivity: Flow<ConnectivityObserver.Status>
        get() = TODO("Not yet implemented")

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, ChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(name: String): Either<ResponseError, FindChannelsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(): Either<ResponseError, FindChannelsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMore(name: String): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

}