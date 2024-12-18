package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.users.User
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.screens.channels.model.ChannelsServices
import com.example.chimp.screens.channels.model.FetchChannelsResult
import com.example.chimp.services.http.dtos.input.channel.ChannelInputModel
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.dtos.input.channel.ChannelListInputModel
import com.example.chimp.services.http.dtos.input.channel.toChannelInfo
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

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
    private val url: String,
    private val user: Flow<User?>
) : ChannelsServices {
    private val _channels = MutableStateFlow<List<ChannelBasicInfo>>(emptyList())
    private val _hasMore = MutableStateFlow(false)
    private var idx = 0
    private val limit = 10
    private val hasMore = limit + 1
    private val api = "$url/api/channels"

    override suspend fun fetchChannels(): Either<ResponseError, FetchChannelsResult> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        idx = 0
        client
            .get("$api/my?limit=$hasMore") { makeHeader(curr) }
            .let { response ->
                try {
                    return when(response.status) {
                        HttpStatusCode.OK -> {
                            val channels: List<ChannelListInputModel> = Json.decodeFromString(response.bodyAsText())
                            val hasMore = channels.size == hasMore
                            _channels.emit(channels.toChannelInfo().take(limit))
                            _hasMore.emit(hasMore)
                            success(FetchChannelsResult(_channels, _hasMore))
                        }
                        HttpStatusCode.Unauthorized -> failure(ResponseError.Unauthorized)
                        else -> failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    override suspend fun deleteOrLeave(channel: ChannelBasicInfo): Either<ResponseError, Unit> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        client
            .delete("$api/${channel.cId}") { makeHeader(curr) }
            .let { response ->
                try {
                    if (response.status == HttpStatusCode.OK) {
                        _channels.emit(_channels.value.filter { it.cId != channel.cId })
                        return success(Unit)
                    } else {
                        return failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }


    override suspend fun fetchChannelInfo(channel: ChannelBasicInfo): Either<ResponseError, ChannelInfo> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        client
            .get("$api/${channel.cId}") { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK)
                        success(response.body<ChannelInputModel>().toChannelInfo())
                    else
                        failure(response.body<ErrorInputModel>().toResponseError())
                } catch (e: Exception) {
                    Log.e(CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        Log.i(CHANNELS_SERVICE_TAG, "Fetching more channels")
        idx += limit
        client
            .get("$api/my?offset=$idx&limit=$hasMore") { makeHeader(curr) }
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
                    Log.e(CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    companion object {
        const val CHANNELS_SERVICE_TAG = "ChannelsService"
    }
}