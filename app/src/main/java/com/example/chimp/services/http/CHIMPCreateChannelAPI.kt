package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.Visibility
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.User
import com.example.chimp.screens.createChannel.model.CreateChannelService
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class CHIMPCreateChannelAPI(
    private val client: HttpClient,
    private val url: String,
    private val user: Flow<User?>
) : CreateChannelService {
    private val _channels = MutableStateFlow<List<ChannelBasicInfo>>(emptyList())

    override suspend fun fetchChannelsByNames(channelName: String): Either<ResponseError, List<ChannelBasicInfo>> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        client
            .get("$url$MY_CHANNELS_URL") { makeHeader(curr) }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        val channels = response.body<List<ChannelBasicInfo>>()
                        val ownedChannels = channels.filter { it.owner.id == curr.id }
                        val channel = ownedChannels.filter { it.name.name.lowercase() == channelName.lowercase() }
                        if (channel.isNotEmpty()) {
                            success(channel[0])
                        } else {
                            failure(ResponseError.NotFound)
                        }
                        TODO("fix to flow")
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(CREATE_CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) } ?: ResponseError.Unknown)
                }
            }

    }

    override suspend fun createChannel(
        channelName: String,
        visibility: Visibility,
        accessControl: AccessControl
    ): Either<ResponseError, Unit> {
        val curr = user.first() ?: return Either.Left(ResponseError.Unauthorized)
        client
            .post("$url$CHANNEL_BASE_URL") {
                makeHeader(curr)
                setBody(
                    mapOf(
                        "name" to channelName,
                        "visibility" to visibility,
                        "accessControl" to accessControl
                    )
                )
            }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(Unit)
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(CREATE_CHANNELS_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) } ?: ResponseError.Unknown)
                }
            }
    }


    companion object {
        const val CHANNEL_BASE_URL = "/api/channels"

        const val MY_CHANNELS_URL = "$CHANNEL_BASE_URL/my"

        const val CREATE_CHANNELS_SERVICE_TAG = "CreateChannelsService"
    }
}