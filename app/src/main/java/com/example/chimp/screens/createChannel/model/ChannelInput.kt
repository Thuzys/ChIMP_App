package com.example.chimp.screens.createChannel.model

import androidx.annotation.DrawableRes
import com.example.chimp.R
import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.Visibility

data class ChannelInput(
    val channelName: String,
    val visibility: Visibility,
    val accessControl: AccessControl,
    val description: String? = null,
    @DrawableRes val icon: Int = R.drawable.github_mark
)
