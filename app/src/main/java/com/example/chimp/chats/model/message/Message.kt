package com.example.chimp.chats.model.message

import com.example.chimp.chats.model.channel.ChannelName
import com.example.chimp.chats.model.users.UserName
import java.sql.Timestamp

data class Message(
    val content: String,
    val msgId: UInt,
    val owner: UserName,
    val channel: ChannelName,
    val creationDate: Timestamp
)


