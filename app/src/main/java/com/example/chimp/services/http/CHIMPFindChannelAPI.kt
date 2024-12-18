package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.Serializable
import java.security.acl.Owner

/**
 * Implementation of the FindChannelsService that fetches channel data from a remote server using HTTP.
 * @param client The HTTP client to use for fetching channel data.
 * @param url The URL of the remote server.
 */
class CHIMPFindChannelAPI(
    private val client: HttpClient,
    private val url: String
): FindChannelService {

    private val unknownError = "Unknown error, please verify internet connection and try again later."

    override suspend fun joinChannel(channelId: UInt): Either<ResponseError, ChannelBasicInfo> =
        client
            .put("$url$CHANNEL_BASE_URL") {
                contentType(ContentType.Application.Json)
                contentType(ContentType.Application.ProblemJson)
                setBody(mapOf("cId" to channelId))
            }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(response.body<ChannelDto>().toChannelBasicInfo())
                    } else {
                        failure(response.body<ErrorDto>().toResponseErrors())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }

            }

    override suspend fun findChannelsByPartialName(channelName: ChannelName): Either<ResponseError, Flow<List<ChannelBasicInfo>>> {
        client
            .get("$CHANNEL_PUBLIC_BASE_URL/${channelName.name}")
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(flowOf(response.body<List<ChannelDto>>().map { it.toChannelBasicInfo() }))
                    } else {
                        failure(response.body<ErrorDto>().toResponseErrors())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?,
    ): Either<ResponseError, Flow<List<ChannelBasicInfo>>> {
        client
            .get("$CHANNEL_PUBLIC_BASE_URL?offset=$offset&limit=$limit")
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(flowOf(response.body<List<ChannelDto>>().map { it.toChannelBasicInfo() }))
                    } else {
                        failure(response.body<ErrorDto>().toResponseErrors())
                    }
                } catch (e: Exception) {
                    Log.e(FIND_CHANNEL_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }
    }

    @Serializable
    private data class ErrorDto(
        val title: String,
        val type: String,
    ) {
        fun toResponseErrors() = ResponseError(cause = title, urlInfo = type)
    }

    @Serializable
    private data class ChannelDto(
        val id: UInt,
        val name: ChannelNameOutputModel,
        val owner: OwnerOutputDto,
        val description: String? = null,
        val icon: String? = null,
    ) {
        fun toChannelBasicInfo() = ChannelBasicInfo(
            cId = id,
            name = name.toChannelName(),
            owner = UserInfo(owner.id, owner.name),
        )
    }

    @Serializable
    private data class OwnerOutputDto(
        val id: UInt,
        val name: String,
    )

    @Serializable
    private data class ChannelNameOutputModel(
        val name: String,
        val displayName: String,
    ) {
        fun toChannelName() = ChannelName(name)
    }

    companion object {
        /**
         * the base url for the channels endpoints
         */
        const val CHANNEL_BASE_URL = "/api/channels"

        const val CHANNEL_PUBLIC_BASE_URL = "$CHANNEL_BASE_URL/public"

        /**
         * the tag for the find channel service
         */
        const val FIND_CHANNEL_SERVICE_TAG = "FindChannelService"
    }
}