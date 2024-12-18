package com.example.chimp.services.http.dtos.input.user

import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
internal data class AuthUserInputModel(
    val uId: UInt,
    val token: String,
    val expirationDate: String
) {
    fun toUser(name: String) =
        User(id = uId, name = name, token = Token(token, Timestamp.valueOf(expirationDate)))
}
