package com.example.chimp.services.dummy

import com.example.chimp.either.Either
import com.example.chimp.either.failure
import com.example.chimp.either.success
import com.example.chimp.login.model.LoginService
import com.example.chimp.model.errors.ResponseErrors
import com.example.chimp.model.users.User
import kotlinx.coroutines.delay

class DummyLoginService: LoginService {
    override suspend fun login(
        username: String,
        password: String
    ): Either<ResponseErrors, User> {
        delay(3000)
        if (username == "error") {
            return failure(
                ResponseErrors(
                    "Invalid username",
                    "https://github.com/isel-leic-daw/2024-daw-leic52d-im-i52d-2425-g04/" +
                            "tree/main/docs/problems/user/user-not-found"
                )
            )
        }
        return success(User(1u, username, "dummy_token"))
    }
}