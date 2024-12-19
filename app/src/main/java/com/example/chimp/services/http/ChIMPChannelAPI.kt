package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.User
import com.example.chimp.screens.channel.model.ChannelService
import com.example.chimp.screens.channel.model.FetchMessagesResult
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.dtos.input.message.MessageInputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

/**
 * ChIMPChannelAPI is the implementation of the ChannelService interface.
 *
 * This class is responsible for handling the HTTP requests related to the channel.
 *
 * @property client the HTTP client used to make the requests
 * @property url the base URL for the requests
 * @property user the flow of the current user
 * @property channel the flow of the current channel
 */
class ChIMPChannelAPI(
    private val client: HttpClient,
    private val url: String,
    private val user: Flow<User?>,
    private val channel: Flow<ChannelBasicInfo?>
) : ChannelService {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    private val _hasMore = MutableStateFlow(false)
    private val limit = 10
    private val hasMore = limit + 1
    private val channelApi = "$url/api/channel"
    private val messagesApi = "$url/api/messages"

    override suspend fun fetchMessages(): Either<ResponseError, FetchMessagesResult> {
        val currUser = user.first() ?: return failure(ResponseError.Unauthorized)
        val currChannel = channel.first() ?: return failure(ResponseError.InternalServerError)
        client
            .get("$messagesApi/channel/${currChannel.cId}/timestamp?limit=$hasMore") {
                makeHeader(currUser)
            }
            .let { response ->
                try {
                    return when(response.status) {
                        HttpStatusCode.OK -> {
                            val messages: List<MessageInputModel> =
                                Json.decodeFromString(response.bodyAsText())
                            val hasMore = messages.size == hasMore
                            _messages.emit(messages.map(MessageInputModel::toMessage))
                            _hasMore.emit(hasMore)
                            success(FetchMessagesResult(_messages, _hasMore))
                        }
                        else -> {
                            failure(response.body<ErrorInputModel>().toResponseError())
                        }
                    }
                } catch (e: Exception) {
                    Log.e(CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    override suspend fun fetchMore(): Either<ResponseError, Unit> {
        val currUser = user.first() ?: return failure(ResponseError.Unauthorized)
        val currChannel = channel.first() ?: return failure(ResponseError.InternalServerError)
        val lastTimestamp = _messages.value.last().time.toString()
        val uri = "$messagesApi/channel/${currChannel.cId}/timestamp?limit=$hasMore&timestamp=$lastTimestamp&isBefore=false"
        client
            .get(uri) { makeHeader(currUser) }
            .let { response ->
                try {
                    return when(response.status) {
                        HttpStatusCode.OK -> {
                            val messages: List<MessageInputModel> =
                                Json.decodeFromString(response.bodyAsText())
                            val hasMore = messages.size == hasMore
                            _messages.emit(_messages.value + messages.map(MessageInputModel::toMessage))
                            _hasMore.emit(hasMore)
                            success(Unit)
                        }
                        else -> {
                            failure(response.body<ErrorInputModel>().toResponseError())
                        }
                    }
                } catch (e: Exception) {
                    Log.e(CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }

    override suspend fun sendMessage(message: Message): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchChannelInfo(): Either<ResponseError, ChannelInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun updateChannelInfo(channel: ChannelInfo): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrLeaveChannel(): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAccessControl(): Either<ResponseError, AccessControl> {
        return success(READ_WRITE)
    }

    companion object {
        private const val CHANNEL_SERVICE_TAG = "ChannelService"
    }
}