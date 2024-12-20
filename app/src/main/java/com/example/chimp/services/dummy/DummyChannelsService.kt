package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.channels.model.FetchChannelsResult
import kotlinx.coroutines.flow.flowOf

class DummyChannelsService : ChannelsServices {
    override suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult> {
        return success(Pair(flowOf(emptyList()), flowOf(false)))
    }

    override suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseError, Unit> {
        return success(Unit)
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo> {
        return success(ChannelInfo(1u, ChannelName("name", "name"), owner = UserInfo(1u, "owner")))
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        return success(Unit)
    }
}