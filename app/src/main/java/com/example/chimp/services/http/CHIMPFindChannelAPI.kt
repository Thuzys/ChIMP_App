package com.example.chimp.services.http

import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

/**
 * Implementation of the FindChannelsService that fetches channel data from a remote server using HTTP.
 * @param client The HTTP client to use for fetching channel data.
 * @param url The URL of the remote server.
 */
class CHIMPFindChannelAPI(
    private val client: HttpClient,
    private val url: String
): FindChannelService {
    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, Unit> =
        client.put("$url$CHANNEL_BASE_URL/$channelId")
            .let { response ->
                return if (response.status == HttpStatusCode.OK) {
                    success(Unit)
                } else {
                    failure(response.body<ErrorDto>().toResponseErrors())
                }
            }

    override suspend fun findChannelByName(channelName: ChannelName): Either<ResponseError, ChannelBasicInfo> =
        client
            .get("$url$CHANNEL_NAME_URL${channelName.encode()}")
            .let { response ->
                return if (response.status == HttpStatusCode.OK) {
                    val channel = response.body<FindChannelDto>().toChannelBasicInfo()
                    success(channel)
                } else {
                    failure(response.body<ErrorDto>().toResponseErrors())
                }
            }

    override suspend fun findChannelsByPartialName(channelName: ChannelName): Either<ResponseErrors, Flow<List<ChannelBasicInfo>>> {
        TODO("Not yet implemented")
    }

    override suspend fun findChannel(channelName: ChannelName): Either<ResponseError, FindChannelItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?,
    ): Either<ResponseError, Flow<List<ChannelBasicInfo>>> {
        TODO("Not yet implemented")
    }

    @Serializable
    private data class ErrorDto(
        val title: String,
        val type: String,
    ) {
        fun toResponseErrors() = ResponseError(cause = title, urlInfo = type)
    }

    @Serializable
    private data class FindChannelDto(
        val id: UInt,
        val name: String,
        val icon: Int,
        val owner: OwnerOutputDto,
    ) {
        fun toChannelBasicInfo() = ChannelBasicInfo(id, ChannelName(name), icon)
    }

    @Serializable
    private data class OwnerOutputDto(
        val id: UInt,
        val name: String,
    )

    companion object {
        /**
         * the base url for the channels endpoints
         */
        const val CHANNEL_BASE_URL = "/channels"

        /**
         * the url for the channel with the given name
         */
        const val CHANNEL_NAME_URL = "$CHANNEL_BASE_URL/name/"

        /**
         * the url for the channel with the given partial name
         */
    }
}