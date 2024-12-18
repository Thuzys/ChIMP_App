package com.example.chimp.screens.chats.model.channel

import androidx.annotation.DrawableRes
import com.example.chimp.R

data class ChannelBasicInfo(
    val cId: UInt,
    val name: ChannelName,
    @DrawableRes val icon: Int = R.drawable.github_mark,
)
