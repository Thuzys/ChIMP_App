package com.example.chimp.services.dummy

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.channels.model.FetchChannelsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DummyChannelsService : ChannelsServices {
    override val connectivity: Flow<ConnectivityObserver.Status>
        get() = TODO("Not yet implemented")

    override suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult> {
        return success(Pair(flowOf(emptyList()), flowOf(false)))
    }

    override suspend fun deleteOrLeave(channel: ChannelInfo): Either<ResponseError, Unit> {
        return success(Unit)
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        return success(Unit)
    }

    override suspend fun createUserInvitation(date: String): Either<ResponseError, String> {
        TODO("Not yet implemented")
    }


    override suspend fun joinChannel(invitationCode: String): Either<ResponseError, ChannelInfo> {
        TODO("Not yet implemented")
    }
}