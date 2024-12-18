package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.screens.findChannel.model.FindChannelItem
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class DummyFindChannelService: FindChannelService {

    private val channels = mutableListOf(
        FindChannelItem(
            cId = 1u,
            name = ChannelName("Channel 1"),
        ),
        FindChannelItem(
            cId = 2u,
            name = ChannelName("Channel 2"),
        ),
        FindChannelItem(
            cId = 3u,
            name = ChannelName("Channel 3"),
        ),
    )

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannel(channelName: ChannelName): Either<ResponseError, FindChannelItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseError, Flow<FindChannelItem>> {
        return success(channels.asFlow())
    }
}