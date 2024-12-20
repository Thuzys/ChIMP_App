package com.example.chimp.screens.channel.model

import com.example.chimp.models.users.User
import java.sql.Timestamp

data class Message(
    val owner: User,
    val message: String,
    val time: Timestamp,
    val mId: UInt,
    val cId: UInt,
)
