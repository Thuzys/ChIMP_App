package com.example.chimp.screens.chats.model.messages

import com.example.chimp.models.users.User
import java.sql.Timestamp

data class Messages(
    val owner: User,
    val message: String,
    val time: Timestamp,
    val mId: UInt,
    val cId: UInt,
)
