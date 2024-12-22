package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.User
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.DISCONNECTED
import com.example.chimp.screens.findChannel.model.FindChannelsResult
import com.example.chimp.services.http.dtos.input.channel.ChannelInputModel
import com.example.chimp.services.http.dtos.input.channel.ChannelListInputModel
import com.example.chimp.services.http.dtos.input.channel.toChannelInfo
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.dtos.output.channel.JoinChannelOutputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

/**
 * Implementation of the FindChannelsService that fetches channel data from a remote server using HTTP.
 * @param client The HTTP client to use for fetching channel data.
 * @param url The URL of the remote server.
 */
class CHIMPFindChannelAPI(
    private val client: HttpClient,
    private val url: String,
    private val user: Flow<User?>,
    connection: Flow<Status>
): FindChannelService {
    private val _channels = MutableStateFlow<List<ChannelInfo>>(emptyList())
    private val _hasMore = MutableStateFlow(false)
    private var idx = 0
    private val limit = 10
    private val hasMore = limit + 1
    override val connectivity: Flow<Status> = connection

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, ChannelInfo> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        connectivity.first().let { conn ->
            if (conn == DISCONNECTED) return failure(ResponseError.NoInternet)
        }
        Log.i(FIND_CHANNEL_SERVICE_TAG, "Joining channel $channelId")
        client
            .put("$url$INVITATION_BASE_URL") {
                makeHeader(curr)
                contentType(ContentType.Application.Json)
                contentType(ContentType.Application.ProblemJson)
                setBody(JoinChannelOutputModel(channelId))
            }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        _channels.emit(_channels.value.filter { it.cId != channelId })
                        success(response.body<ChannelInputModel>().toChannelInfo())
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    private suspend fun fetchChannels(url: String): Either<ResponseError, FindChannelsResult> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        connectivity.first().let { conn ->
            if (conn == DISCONNECTED) return failure(ResponseError.NoInternet)
        }
        Log.d(FIND_CHANNEL_SERVICE_TAG, "Fetching channels from $url")
        idx = 0
        client
            .get(url) { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        val channels: List<ChannelListInputModel> = Json.decodeFromString(response.bodyAsText())
                        val hasMore = channels.size == hasMore
                        _channels.emit(channels.toChannelInfo().take(limit))
                        _hasMore.emit(hasMore)
                        success(Pair(_channels, _hasMore))
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    override suspend fun getChannels(name: String): Either<ResponseError, FindChannelsResult> {
        return fetchChannels("$url$CHANNEL_PUBLIC_BASE_URL/$name?limit=$hasMore")
    }

    override suspend fun getChannels(): Either<ResponseError, FindChannelsResult> {
        return fetchChannels("$url$CHANNEL_PUBLIC_BASE_URL?limit=$hasMore")
    }

    private suspend fun fetchMoreChannels(url: String): Either<ResponseError, Unit> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        connectivity.first().let { conn ->
            if (conn == DISCONNECTED) return failure(ResponseError.NoInternet)
        }
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
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        return fetchMoreChannels("$url$CHANNEL_PUBLIC_BASE_URL?offset=$idx&limit=$hasMore")
    }

    override suspend fun fetchMore(name: String): Either<ResponseError, Unit> {
        return fetchMoreChannels("$url$CHANNEL_PUBLIC_BASE_URL/$name?offset=$idx&limit=$hasMore")
    }

    companion object {
        /**
         * the base url for the channels endpoints
         */
        private const val CHANNEL_BASE_URL = "/api/channels"

        /**
         * the base url for the public channels endpoints
         */
        const val CHANNEL_PUBLIC_BASE_URL = "$CHANNEL_BASE_URL/public"

        /**
         * the base url for the invitations endpoints
         */
        const val INVITATION_BASE_URL = "$CHANNEL_BASE_URL/invitations"

        /**
         * the tag for the find channel service
         */
        const val FIND_CHANNEL_SERVICE_TAG = "FindChannelService"
    }
}