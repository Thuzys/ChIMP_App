package com.example.chimp.screens.register.service

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import com.example.chimp.screens.register.model.RegisterService
import kotlinx.coroutines.channels.Channel

internal class FakeService : RegisterService {
    private val controller = Channel<Unit>()
    val validUsername = "test"
    val validPassword = "test"
    private val validInvitationCode = "test"
    private val user = User(
        id = 1u,
        name = validUsername,
        token = Token("token")
    )
    override suspend fun login(
        username: String,
        password: String
    ): Either<ResponseError, User> {
        controller.receive()
        if (username == validUsername && password == validPassword) {
            return success(
                User(
                    id = 1u,
                    name = "test",
                    token = Token("token")
                )
            )
        } else {
            return failure(
                ResponseError(
                    cause = "Invalid username or password",
                    urlInfo = "https://example.com"
                )
            )
        }
    }
    suspend fun unlock() {
        controller.send(Unit)
    }
    override suspend fun register(
        username: String,
        password: String,
        invitationCode: String
    ): Either<ResponseError, User> {
        if (
            username == validUsername &&
            password == validPassword &&
            invitationCode == validInvitationCode
        ) {
            controller.receive()
            return success(user)
        } else {
            return failure(
                ResponseError(
                    cause = "Invalid username or password",
                    urlInfo = "https://example.com"
                )
            )
        }
    }

}