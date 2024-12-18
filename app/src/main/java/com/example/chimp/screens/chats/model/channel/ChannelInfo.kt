package com.example.chimp.screens.chats.model.channel

import com.example.chimp.models.users.User

data class ChannelInfo(
    val cId: UInt,
    val name: ChannelName,
    val members: List<User>,
    val owner: User,
)