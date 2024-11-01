package com.example.chimp.findChannel.model

import androidx.annotation.DrawableRes
import com.example.chimp.chats.model.channel.ChannelName

data class FindChannelItem(
    val cId: UInt,
    val name: ChannelName,
    @DrawableRes val icon: Int,
)
