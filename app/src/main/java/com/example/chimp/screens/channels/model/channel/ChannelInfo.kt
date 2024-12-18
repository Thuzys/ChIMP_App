package com.example.chimp.screens.channels.model.channel

import com.example.chimp.R
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.UserInfo

data class ChannelInfo(
    val cId: UInt,
    val name: ChannelName,
    val description: String? = null,
    val owner: UserInfo,
    val icon: Int = R.drawable.user_mark
)