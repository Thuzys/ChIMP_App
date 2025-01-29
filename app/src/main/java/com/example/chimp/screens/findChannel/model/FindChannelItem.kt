package com.example.chimp.screens.findChannel.model

import androidx.annotation.DrawableRes
import com.example.chimp.R
import com.example.chimp.models.channel.ChannelName

data class FindChannelItem(
    val cId: UInt,
    val name: ChannelName,
    @DrawableRes val icon: Int = R.drawable.github_mark,
)
