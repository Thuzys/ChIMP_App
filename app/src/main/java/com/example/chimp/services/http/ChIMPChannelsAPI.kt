package com.example.chimp.services.http

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.screens.channels.model.channel.ChannelInfo
import com.example.chimp.screens.channels.model.channel.ChannelsServices
import com.example.chimp.screens.channels.model.channel.FetchChannelsResult
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * ChIMPChannelsAPI is the implementation of the ChannelsServices interface.
 *
 * This class is responsible for handling the HTTP requests related to the channels.
 *
 * @property client the HTTP client used to make the requests
 * @property url the base URL for the requests
 */
class ChIMPChannelsAPI(
    private val client: HttpClient,
    private val url: String
) : ChannelsServices {
    private val _channels = MutableStateFlow<List<ChannelBasicInfo>>(emptyList())
    private var idx = 0
    private val api = "$url/api/channels/my"

    override suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }
}