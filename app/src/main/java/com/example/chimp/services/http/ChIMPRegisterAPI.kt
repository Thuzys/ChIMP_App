package com.example.chimp.services.http

import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.models.users.User
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseErrors
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.Serializable

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

    @OptIn(InternalAPI::class)
    override suspend fun login(
        username: String,
        password: String,
    ): Either<ResponseErrors, User> =
        client
        .post("$api/login") {
            body = mapOf(
                "username" to username,
                "password" to password
            )
        }
        .let { response ->
            return if (response.status == HttpStatusCode.OK) {
                success(response.body<UserDto>().toUser(username))
            } else {
                failure(response.body<ErrorDto>().toResponseErrors())
            }
        }

    @OptIn(InternalAPI::class)
    override suspend fun register(
        username: String,
        password: String,
        invitationCode: String
    ): Either<ResponseErrors, User> {
        client
            .post("$api/signup") {
                body = mapOf(
                    "username" to username,
                    "password" to password,
                    "invitationCode" to invitationCode
                )
            }.let { response ->
                return if (response.status == HttpStatusCode.OK) {
                    success(response.body<UserDto>().toUser(username))
                } else {
                    failure(response.body<ErrorDto>().toResponseErrors())
                }
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
    private data class UserDto (
        val uId: UInt,
        val token: String
    ) {
        fun toUser(name: String) = User(id = uId, name = name, token = token)
    }
}