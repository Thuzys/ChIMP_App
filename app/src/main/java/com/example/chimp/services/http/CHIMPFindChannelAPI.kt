package com.example.chimp.services.http

import com.example.chimp.chats.model.channel.ChannelName
import com.example.chimp.either.Either
import com.example.chimp.either.failure
import com.example.chimp.either.success
import com.example.chimp.findChannel.model.FindChannelItem
import com.example.chimp.findChannel.model.FindChannelService
import com.example.chimp.model.errors.ResponseErrors
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    override suspend fun joinChannel(channelId: UInt): Either<ResponseErrors, Unit> =
        client.put("$url$CHANNEL_BASE_URL/$channelId")
            .let { response ->
                return if (response.status == HttpStatusCode.OK) {
                    success(Unit)
                } else {
                    failure(response.body<ErrorDto>().toResponseErrors())
                }
            }

    override suspend fun findChannel(channelName: ChannelName): Either<ResponseErrors, FindChannelItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseErrors, Flow<FindChannelItem>> =
        client
            .get(buildString {
                append("$url$CHANNEL_BASE_URL")
                val queryParams = listOfNotNull(
                    offset?.let { "offset=$it" },
                    limit?.let { "limit=$it" }
                )
                if (queryParams.isNotEmpty()) {
                    append("?")
                    append(queryParams.joinToString("&"))
                }
            })
            .let { response ->
                return if (response.status == HttpStatusCode.OK) {
                    val channels = response.body<List<FindChannelDto>>().map(FindChannelDto::toFindChannelItem)
                    success(flowOf(*channels.toTypedArray()))
                } else {
                    failure(response.body<ErrorDto>().toResponseErrors())
                }
            }

    @Serializable
    private data class ErrorDto(
        val title: String,
        val type: String,
    ) {
        fun toResponseErrors() = ResponseErrors(cause = title, urlInfo = type)
    }

    @Serializable
    private data class FindChannelDto(
        val id: UInt,
        val name: String,
        val icon: Int
    ) {
        fun toFindChannelItem() = FindChannelItem(id, ChannelName(name), icon)
    }

    companion object {
        /**
         * the base url for the channels endpoints
         */
        const val CHANNEL_BASE_URL = "/channels"
    }
}