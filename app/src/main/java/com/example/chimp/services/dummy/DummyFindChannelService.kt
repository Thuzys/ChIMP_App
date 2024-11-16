package com.example.chimp.services.dummy

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelItem
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class DummyFindChannelService: FindChannelService {

    private val channels = mutableListOf(
        FindChannelItem(
            cId = 1u,
            name = ChannelName("Channel 1"),
            icon = TODO(),
        ),
        FindChannelItem(
            cId = 2u,
            name = ChannelName("Channel 2"),
            icon = TODO(),
        ),
        FindChannelItem(
            cId = 3u,
            name = ChannelName("Channel 3"),
            icon = TODO(),
        ),
    )

    override suspend fun joinChannel(channelId: UInt): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannelByName(channelName: ChannelName): Either<ResponseErrors, FindChannelItem> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannelsByPartialName(channelName: ChannelName): Either<ResponseErrors, Flow<FindChannelItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseErrors, Flow<FindChannelItem>> {
        return success(channels.asFlow())
    }
}