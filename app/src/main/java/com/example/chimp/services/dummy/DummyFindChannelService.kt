package com.example.chimp.services.dummy

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.screens.chats.model.channel.ChannelBasicInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

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

    override suspend fun joinChannel(channelId: UInt, invitationCode: String?): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannelByName(channelName: ChannelName): Either<ResponseErrors, ChannelBasicInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannelsByPartialName(channelName: ChannelName): Either<ResponseErrors, Flow<ChannelBasicInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseErrors, Flow<ChannelBasicInfo>> {
        return success(channels.asFlow())
    }
}