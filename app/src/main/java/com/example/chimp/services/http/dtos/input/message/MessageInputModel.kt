package com.example.chimp.services.http.dtos.input.message

import com.example.chimp.models.message.Message
import com.example.chimp.models.users.UserInfo
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
internal data class MessageInputModel(
    val id: UInt,
    val message: String,
    val user: UInt,
    val username: String,
    val channel: UInt,
    val creationTime: String,
) {
    fun toMessage() = Message(
        mId = id,
        message = message,
        owner = UserInfo(user, username),
        cId = channel,
        time = Timestamp.valueOf(creationTime)
    )
}
