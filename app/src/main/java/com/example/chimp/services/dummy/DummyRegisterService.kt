package com.example.chimp.services.dummy

import com.example.chimp.models.either.Either
import com.example.chimp.models.either.failure
import com.example.chimp.models.either.success
import com.example.chimp.screens.register.model.RegisterService
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import kotlinx.coroutines.delay

class DummyRegisterService: RegisterService {

    private val token = Token("dummy_token")

    override suspend fun login(
        username: String,
        password: String
    ): Either<ResponseError, User> {
        delay(3000)
        if (username == "error") {
            return failure(
                ResponseError(
                    "Invalid username",
                    "https://github.com/isel-leic-daw/2024-daw-leic52d-im-i52d-2425-g04/" +
                            "tree/main/docs/problems/user/user-not-found"
                )
            )
        }
        return success(User(1u, username, token))
    }

    override suspend fun register(
        username: String,
        password: String,
        invitationCode: String
    ): Either<ResponseError, User> {
        delay(3000)
        if (username == "error") {
            return failure(
                ResponseError(
                    "Invalid username",
                    "dummy_url"
                )
            )
        }
        return success(User(1u, username, token))
    }
}