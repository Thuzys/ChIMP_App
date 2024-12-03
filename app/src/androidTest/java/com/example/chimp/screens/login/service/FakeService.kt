package com.example.chimp.screens.login.service

import com.example.chimp.either.Either
import com.example.chimp.either.failure
import com.example.chimp.either.success
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.login.model.LoginService
import kotlinx.coroutines.channels.Channel

internal class FakeService : LoginService {
    private val controller = Channel<Unit>()
    val validUsername = "test"
    val validPassword = "test"
    val validInvitationCode = "test"
    override suspend fun login(
        username: String,
        password: String
    ): Either<ResponseErrors, User> {
        controller.receive()
        if (username == validUsername && password == validPassword) {
            return success(
                User(
                    id = 1u,
                    name = "test",
                    token = "test"
                )
            )
        } else {
            return failure(
                ResponseErrors(
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
    ): Either<ResponseErrors, User> {
        if (
            username == validUsername &&
            password == validPassword &&
            invitationCode == validInvitationCode
        ) {
            controller.receive()
            return success(
                User(
                    id = 1u,
                    name = "test",
                    token = "test"
                )
            )
        } else {
            return failure(
                ResponseErrors(
                    cause = "Invalid username or password",
                    urlInfo = "https://example.com"
                )
            )
        }
    }

}