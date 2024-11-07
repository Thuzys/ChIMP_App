package com.example.chimp.services.http

import com.example.chimp.screens.login.model.LoginService
import com.example.chimp.models.users.User
import com.example.chimp.either.Either
import com.example.chimp.either.failure
import com.example.chimp.either.success
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
class ChIMPLoginAPI(
    private val client: HttpClient,
    private val url: String
) : LoginService {

    @OptIn(InternalAPI::class)
    override suspend fun login(
        username: String,
        password: String,
    ): Either<ResponseErrors, User> =
        client
        .post(url) {
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

    override suspend fun register(
        username: String,
        password: String,
        invitationCode: String
    ): Either<ResponseErrors, User> {
        TODO("Not yet implemented")
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