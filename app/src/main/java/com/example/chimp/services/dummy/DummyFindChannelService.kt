package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.findChannel.model.FindChannelsResult

class DummyFindChannelService: FindChannelService {

    private val channels = mutableListOf(
        ChannelBasicInfo(
            cId = 1u,
            name = ChannelName("Channel 1", "Channel 1"),
            owner = UserInfo(1u, "Owner 1"),
        ),
        ChannelBasicInfo(
            cId = 2u,
            name = ChannelName("Channel 2", "Channel 2"),
            owner = UserInfo(2u, "Owner 2"),
        ),
        ChannelBasicInfo(
            cId = 3u,
            name = ChannelName("Channel 3", "Channel 3"),
            owner = UserInfo(3u, "Owner 3"),
        ),
    )

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, ChannelBasicInfo> {
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

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo> {
        TODO("Not yet implemented")
    }
}