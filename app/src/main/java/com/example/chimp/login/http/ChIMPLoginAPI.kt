package com.example.chimp.login.http

import com.example.chimp.login.model.LoginErrors
import com.example.chimp.login.model.LoginService
import com.example.chimp.model.User
import com.example.chimp.model.utils.Either
import com.example.chimp.model.utils.failure
import com.example.chimp.model.utils.success
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.readRawBytes
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.InternalAPI
import kotlinx.serialization.Serializable
import java.net.URL

class ChIMPLoginAPI(private val client: HttpClient) : LoginService {
    private val url = "TODO"
    private val source = URL(url)

    @OptIn(InternalAPI::class)
    override suspend fun login(
        username: String,
        password: String,
    ): Either<LoginErrors, User> =
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
                failure(LoginErrors.InvalidCredentials(response.readRawBytes().toString()))
            }
        }

    @Serializable
    private data class UserDto (
        val uId: UInt,
        val token: String
    ) {
        fun toUser(name: String) = User(id = uId, name = name, token = token)
    }
}