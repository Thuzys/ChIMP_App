package com.example.chimp.services.http

import android.util.Log
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.models.users.User
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.services.http.dtos.input.user.AuthUserInputModel
import com.example.chimp.services.http.dtos.input.error.ErrorInputModel
import com.example.chimp.services.http.utlis.makeHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

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

    override suspend fun login(
        username: String,
        password: String,
    ): Either<ResponseError, User> =
        client
            .post("$api/login") {
                makeHeader()
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
                        success(response.body<AuthUserInputModel>().toUser(username))
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(REGISTER_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) } ?: ResponseError.Unknown)
                }
            }

    override suspend fun register(
        username: String,
        password: String,
        invitationCode: String
    ): Either<ResponseError, User> {
        client
            .post("$api/signup") {
                makeHeader()
                setBody(mapOf(
                    "username" to username,
                    "password" to password,
                    "invitationCode" to invitationCode
                ))
            }.let { response ->
                try {
                    return if (response.status == HttpStatusCode.OK) {
                        success(response.body<AuthUserInputModel>().toUser(username))
                    } else {
                        failure(response.body<ErrorInputModel>().toResponseError())
                    }
                } catch (e: Exception) {
                    Log.e(REGISTER_SERVICE_TAG, "Error: ${e.message}")
                    return failure(e.message?.let { ResponseError(cause = it) } ?: ResponseError.Unknown)
                }
            }
    }


    companion object {
        const val REGISTER_SERVICE_TAG = "RegisterService"
    }
}