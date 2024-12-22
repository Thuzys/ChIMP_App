package com.example.chimp.services.http.dtos.output.message

import com.example.chimp.models.message.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageOutputModel(
    val msg: String,
    val channel: UInt
) {
    companion object {
        fun fromMessage(msg: Message): MessageOutputModel =
            MessageOutputModel(
                msg = msg.message,
                channel = msg.cId
            )
    }
}
