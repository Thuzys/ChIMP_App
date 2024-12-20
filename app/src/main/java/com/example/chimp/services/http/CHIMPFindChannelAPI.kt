package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.User
import com.example.chimp.screens.findChannel.model.FindChannelsResult
import com.example.chimp.services.http.dtos.input.channel.ChannelInputModel
import com.example.chimp.services.http.dtos.input.channel.ChannelListInputModel
import com.example.chimp.services.http.dtos.input.channel.toChannelInfo
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

/**
 * Implementation of the FindChannelsService that fetches channel data from a remote server using HTTP.
 * @param client The HTTP client to use for fetching channel data.
 * @param url The URL of the remote server.
 */
class CHIMPFindChannelAPI(
    private val client: HttpClient,
    private val url: String,
    private val user: Flow<User?>
): FindChannelService {
    private val _channels = MutableStateFlow<List<ChannelBasicInfo>>(emptyList())
    private val _hasMore = MutableStateFlow(false)
    private var idx = 0
    private val limit = 10
    private val hasMore = limit + 1

    private val unknownError = "Unknown error, please verify internet connection and try again later."

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, ChannelInfo> =
        client
            .put("$url$CHANNEL_BASE_URL") {
                contentType(ContentType.Application.Json)
                contentType(ContentType.Application.ProblemJson)
                setBody(mapOf("cId" to channelId))
            }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(response.body<ChannelInputModel>().toChannelInfo())
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }

    private suspend fun fetchChannels(url: String): Either<ResponseError, FindChannelsResult> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        idx = 0
        client
            .get(url) { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        val channels = response.body<List<ChannelListInputModel>>()
                        val hasMore = channels.size == hasMore
                        _channels.emit(channels.toChannelInfo().take(limit))
                        _hasMore.emit(hasMore)
                        success(Pair(_channels, _hasMore))
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }
    }

    override suspend fun getChannels(name: String): Either<ResponseError, FindChannelsResult> {
        return fetchChannels("$CHANNEL_PUBLIC_BASE_URL/$name?limit=$hasMore")
    }

    override suspend fun getChannels(): Either<ResponseError, FindChannelsResult> {
        return fetchChannels("$CHANNEL_PUBLIC_BASE_URL?limit=$hasMore")
    }

    private suspend fun fetchMoreChannels(url: String): Either<ResponseError, Unit> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        idx += limit
        client
            .get(url) { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        val channels = response.body<List<ChannelListInputModel>>()
                        val hasMore = channels.size == hasMore
                        _channels.emit(_channels.value + channels.toChannelInfo().take(limit))
                        _hasMore.emit(hasMore)
                        success(Unit)
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        return fetchMoreChannels("$CHANNEL_PUBLIC_BASE_URL?offset=$idx&limit=$hasMore")
    }

    override suspend fun fetchMore(name: String): Either<ResponseError, Unit> {
        return fetchMoreChannels("$CHANNEL_PUBLIC_BASE_URL/$name&offset=$idx&limit=$hasMore")
    }

    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        client
            .get("$url$CHANNEL_BASE_URL/${channel.cId}") { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK)
                        success(response.body<ChannelInputModel>().toChannelInfo())
                    else
                        failure(response.body<ErrorInputModel>().toResponseError())
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }
    }

    companion object {
        /**
         * the base url for the channels endpoints
         */
        const val CHANNEL_BASE_URL = "/api/channels"

        /**
         * the base url for the public channels endpoints
         */
        const val CHANNEL_PUBLIC_BASE_URL = "$CHANNEL_BASE_URL/public"

        /**
         * the tag for the find channel service
         */
        const val FIND_CHANNEL_SERVICE_TAG = "FindChannelService"
    }
}