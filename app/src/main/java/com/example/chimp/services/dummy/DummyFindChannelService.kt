package com.example.chimp.services.dummy

import com.example.chimp.chats.model.channel.ChannelName
import com.example.chimp.either.Either
import com.example.chimp.findChannel.model.FindChannelItem
import com.example.chimp.findChannel.model.FindChannelService
import com.example.chimp.model.errors.ResponseErrors
import kotlinx.coroutines.flow.Flow

class DummyFindChannelService: FindChannelService {
    override suspend fun joinChannel(channelId: UInt): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannel(channelName: ChannelName): Either<ResponseErrors, FindChannelItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseErrors, Flow<FindChannelItem>> {
        TODO("Not yet implemented")
    }
}