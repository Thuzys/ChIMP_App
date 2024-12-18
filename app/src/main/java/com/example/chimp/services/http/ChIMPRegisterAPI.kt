package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.models.users.User
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.Serializable
import java.sql.Timestamp

/**
 * Implementation of the LoginService that fetches user data from a remote server using HTTP.
 * @param client The HTTP client to use for fetching user data.
 * @param url The URL of the remote server.
 */
class ChIMPRegisterAPI(
    private val client: HttpClient,
    private val url: String
) : RegisterService {

    private val api = "$url/api/users"

    private val unknownError = "Unknown error, please verify internet connection and try again later."

    override suspend fun login(
        username: String,
        password: String,
    ): Either<ResponseError, User> =
        client
            .post("$api/login") {
                contentType(ContentType.Application.Json)
                contentType(ContentType.Application.ProblemJson)
                setBody(
                    mapOf(
                        "username" to username,
                        "password" to password
                    )
                )
            }
            .let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(response.body<UserDto>().toUser(username))
                    } else {
                        failure(response.body<ErrorDto>().toResponseErrors())
                    }
                } catch (e: Exception) {
                    Log.e(REGISTER_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }

    @OptIn(InternalAPI::class)
    override suspend fun register(
        username: String,
        password: String,
        invitationCode: String
    ): Either<ResponseError, User> {
        client
            .post("$api/signup") {
                contentType(ContentType.Application.Json)
                contentType(ContentType.Application.ProblemJson)
                body = mapOf(
                    "username" to username,
                    "password" to password,
                    "invitationCode" to invitationCode
                )
            }.let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(response.body<UserDto>().toUser(username))
                    } else {
                        failure(response.body<ErrorDto>().toResponseErrors())
                    }
                } catch (e: Exception) {
                    Log.e(REGISTER_SERVICE_TAG, "Error: ${e.message}")
                    return failure(ResponseError(cause = e.message ?: unknownError))
                }
            }
    }

    @Serializable
    internal data class ErrorDto(
        val type: String,
        val title: String,
    ) {
        fun toResponseErrors() = ResponseError(cause = title, urlInfo = type)
    }

    @Serializable
    internal data class UserDto(
        val uId: UInt,
        val token: String,
        val expirationDate: String
    ) {
        fun toUser(name: String) =
            User(id = uId, name = name, token = Token(token, Timestamp.valueOf(expirationDate)))
    }

    companion object {
        const val REGISTER_SERVICE_TAG = "RegisterService"
    }
}