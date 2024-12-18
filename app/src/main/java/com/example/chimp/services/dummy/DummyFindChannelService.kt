package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DummyFindChannelService: FindChannelService {

    private val channels = mutableListOf(
        ChannelBasicInfo(
            cId = 1u,
            name = ChannelName("Channel 1"),
            icon = TODO(),
        ),
        ChannelBasicInfo(
            cId = 2u,
            name = ChannelName("Channel 2"),
            icon = TODO(),
        ),
        ChannelBasicInfo(
            cId = 3u,
            name = ChannelName("Channel 3"),
            icon = TODO(),
        ),
    )

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannelByName(channelName: ChannelName): Either<ResponseErrors, ChannelBasicInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannelsByPartialName(channelName: ChannelName): Either<ResponseErrors, Flow<List<ChannelBasicInfo>>> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannel(channelName: ChannelName): Either<ResponseError, FindChannelItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseError, Flow<List<ChannelBasicInfo>>> {
        return success(flowOf(channels))
    }
}