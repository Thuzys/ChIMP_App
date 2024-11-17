package com.example.chimp.screens.findChannel.model

import androidx.annotation.DrawableRes
import com.example.chimp.screens.chats.model.channel.ChannelName

data class FindChannelItem(
    val cId: UInt,
    val name: ChannelName,
    @DrawableRes val icon: Int,
)
