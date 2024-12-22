package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.toStrIcon
import com.example.chimp.models.users.User
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.DISCONNECTED
import com.example.chimp.screens.createChannel.model.ChannelInput
import com.example.chimp.screens.createChannel.model.CreateChannelService
import com.example.chimp.services.http.dtos.input.channel.ChannelInputModel
import com.example.chimp.services.http.dtos.input.channel.ChannelListInputModel
import com.example.chimp.services.http.dtos.input.channel.toChannelInfo
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.dtos.output.channel.CreateChannelOutputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class CHIMPCreateChannelAPI(
    private val client: HttpClient,
    private val url: String,
    private val user: Flow<User?>,
    override val connection: Flow<Status>
) : CreateChannelService {

    override suspend fun fetchChannelByNames(channelName: String): Either<ResponseError, ChannelInfo> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        connection.first().let {
            if (it == DISCONNECTED) return Either.Left(ResponseError.NoInternet)
        }
        client
            .get("$url$MY_CHANNELS_URL") { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        val channels =
                            Json.decodeFromString<List<ChannelListInputModel>>(response.bodyAsText())
                                .toChannelInfo()
                        val ownedChannels = channels.filter { it.owner.id == curr.id }
                        val channel =
                            ownedChannels.filter { it.name.displayName.lowercase() == channelName.lowercase() }
                        if (channel.isNotEmpty()) {
                            success(channel[0])
                        } else {
                            failure(ResponseError.NotFound)
                        }
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(CREATE_CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }

    }

    override suspend fun createChannel(
        channelInput: ChannelInput
    ): Either<ResponseError, ChannelInfo> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        connection.first().let {
            if (it == DISCONNECTED) return Either.Left(ResponseError.NoInternet)
        }
        client
            .post("$url$CHANNEL_BASE_URL") {
                makeHeader(curr)
                setBody(
                    CreateChannelOutputModel(
                        curr.id,
                        channelInput.channelName,
                        channelInput.visibility,
                        channelInput.accessControl,
                        channelInput.description,
                        channelInput.icon.toStrIcon()
                    )
                )
            }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(response.body<ChannelInputModel>().toChannelInfo())
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(CREATE_CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) }
                        ?: ResponseError.Unknown)
                }
            }
    }


    companion object {
        const val CHANNEL_BASE_URL = "/api/channels"

        const val MY_CHANNELS_URL = "$CHANNEL_BASE_URL/my"

        const val CREATE_CHANNELS_SERVICE_TAG = "CreateChannelsService"
    }
}