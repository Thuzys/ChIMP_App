package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channels.model.channel.ChannelInfo
import com.example.chimp.screens.channels.model.channel.ChannelsServices
import com.example.chimp.screens.channels.model.channel.FetchChannelsResult
import kotlinx.coroutines.flow.flowOf

class DummyChannelsService : ChannelsServices {
    override suspend fun fetchChannels(): Either<ResponseErrors, FetchChannelsResult> {
        return success(Pair(flowOf(emptyList()), flowOf(false)))
    }

    override suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseErrors, Unit> {
        return success(Unit)
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo> {
        return success(ChannelInfo(1u, ChannelName("name"), owner = UserInfo(1u, "owner")))
    }

    override suspend fun fetchMore(): Either<ResponseErrors, Unit> {
        return success(Unit)
    }
}