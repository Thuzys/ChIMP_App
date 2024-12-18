package com.example.chimp.services.http

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.screens.channels.model.channel.ChannelInfo
import com.example.chimp.screens.channels.model.channel.ChannelsServices
import com.example.chimp.screens.channels.model.channel.FetchChannelsResult
import io.ktor.client.HttpClient

class ChIMPChannelsAPI(private val client: HttpClient): ChannelsServices {
    override suspend fun fetchChannels(): Either<ResponseErrors, FetchChannelsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseErrors, ChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMore(): Either<ResponseErrors, Unit> {
        TODO("Not yet implemented")
    }
}