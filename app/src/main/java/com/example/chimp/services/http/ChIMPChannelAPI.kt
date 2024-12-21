package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelInvitation
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.User
import com.example.chimp.screens.channel.model.ChannelService
import com.example.chimp.screens.channel.model.FetchMessagesResult
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import com.example.chimp.services.http.ChIMPChannelsAPI.Companion.CHANNELS_SERVICE_TAG
import com.example.chimp.services.http.dtos.input.channel.AccessControlInputModel
import com.example.chimp.services.http.dtos.input.channel.ChannelInputModel
import com.example.chimp.services.http.dtos.input.channelInvitation.ChannelInvitationInputModel
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.dtos.input.message.MessageInputModel
import com.example.chimp.services.http.dtos.output.channelInvitation.ChannelInvitationOutputModel
import com.example.chimp.services.http.dtos.output.message.MessageOutputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.sse.sse
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.minutes

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
    private val channelApi = "$url/api/channels"
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
                    return when (response.status) {
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
        val uri =
            "$messagesApi/channel/${currChannel.cId}/timestamp?limit=$hasMore&timestamp=$lastTimestamp"
        client
            .get(uri) { makeHeader(currUser) }
            .let { response ->
                try {
                    return when (response.status) {
                        HttpStatusCode.OK -> {
                            val messages: List<MessageInputModel> =
                                Json.decodeFromString(response.bodyAsText())
                            val hasMore = messages.size == hasMore
                            val newList =
                                _messages.value + messages.map(MessageInputModel::toMessage)
                            _messages.emit(newList.distinct())
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
        val currUser = user.first() ?: return failure(ResponseError.Unauthorized)
        client
            .post(messagesApi)
            {
                makeHeader(currUser)
                setBody(MessageOutputModel.fromMessage(message))
            }.let { response ->
                try {
                    return when (response.status) {
                        HttpStatusCode.OK -> {
                            val newMessage = response.body<MessageInputModel>().toMessage()
                            val newList =
                                if (newMessage !in _messages.value)
                                    listOf(newMessage) + _messages.value
                                else _messages.value
                            _messages.emit(newList)
                            success(Unit)
                        }

                        HttpStatusCode.Unauthorized -> {
                            failure(ResponseError.Unauthorized)
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

    override suspend fun fetchChannelInfo(): Either<ResponseError, ChannelInfo> {
        val currChannel = channel.first() ?: return failure(ResponseError.InternalServerError)
        val currUser = user.first() ?: return failure(ResponseError.Unauthorized)
        client
            .get("$channelApi/${currChannel.cId}") { makeHeader(currUser) }
            .let { response ->
                try {
                    return when (response.status) {
                        HttpStatusCode.OK -> {
                            val channelInfo = response.body<ChannelInputModel>().toChannelInfo()
                            success(channelInfo)
                        }
                        HttpStatusCode.Unauthorized -> {
                            failure(ResponseError.Unauthorized)
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

    override suspend fun updateChannelInfo(channel: ChannelInfo): Either<ResponseError, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOrLeaveChannel(): Either<ResponseError, Unit> {
        val curr = user.first() ?: return failure(ResponseError.Unauthorized)
        val channel = channel.first() ?: return failure(ResponseError.InternalServerError)
        client
            .delete("$channelApi/${channel.cId}") { makeHeader(curr) }
            .let { response ->
                try {
                    if (response.status == HttpStatusCode.OK) {
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

    override suspend fun fetchAccessControl(): Either<ResponseError, AccessControl> {
        val currChannel = channel.first() ?: return failure(ResponseError.InternalServerError)
        val currUser = user.first() ?: return failure(ResponseError.Unauthorized)
        client
            .get("$channelApi/accessControl/${currChannel.cId}")
            { makeHeader(currUser) }
            .let { response ->
                try {
                    return when (response.status) {
                        HttpStatusCode.OK -> {
                            val accessControl =
                                response.body<AccessControlInputModel>().toAccessControl()
                            success(accessControl)
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

    override suspend fun initSseOnMessages() {
        val curr = user.first() ?: return
        try {
            client.sse(
                urlString = "$messagesApi/sse",
                request = { makeHeader(curr) },
                reconnectionTime = 5.minutes
            ) {
                while (true) {
                    incoming.collect { event ->
                        Log.d(CHANNEL_SERVICE_TAG, "Received event: ${event.event}")
                        if (event.event == MESSAGE_EVENT) {
                            event.data?.let {
                                Log.d(CHANNEL_SERVICE_TAG, "Received message: $it")
                                val message =
                                    Json.decodeFromString<MessageInputModel>(it).toMessage()
                                val newList =
                                    if (message !in _messages.value)
                                        listOf(message) + _messages.value
                                    else _messages.value
                                _messages.emit(newList)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(CHANNEL_SERVICE_TAG, "Error: ${e.message}")
        }
    }

    override suspend fun createChannelInvitation(
        channelInvitation: ChannelInvitation
    ): Either<ResponseError, String> {
        val currChannel = channel.first() ?: return failure(ResponseError.InternalServerError)
        val curr = user.first() ?: return failure(ResponseError.Unauthorized)
        val expirationDate =
            channelInvitation.formatTimestamp(channelInvitation.getExpirationTime())
        val maxUses = channelInvitation.maxUses
        val accessControl = channelInvitation.permission
        client
            .post("$channelApi/invitations") {
                makeHeader(curr)
                setBody(
                    ChannelInvitationOutputModel(
                        currChannel.cId,
                        expirationDate,
                        maxUses,
                        accessControl
                    )
                )
            }.let { response ->
                try {
                    return when (response.status) {
                        HttpStatusCode.OK -> {
                           success(response.body<ChannelInvitationInputModel>().invitationCode)
                        }

                        HttpStatusCode.Unauthorized -> {
                            failure(ResponseError.Unauthorized)
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

    companion object {
        private const val CHANNEL_SERVICE_TAG = "ChannelService"
        private const val MESSAGE_EVENT = "message"
    }
}