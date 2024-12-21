package com.example.chimp.models.channel

import androidx.annotation.DrawableRes
import com.example.chimp.R
import com.example.chimp.models.channel.Visibility.PUBLIC
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.accessControl.AccessControl

data class ChannelInfo(
    val cId: UInt,
    val name: ChannelName,
    val description: String? = null,
    val owner: UserInfo,
    val visibility: Visibility = PUBLIC,
    val accessControl: AccessControl = AccessControl.READ_WRITE,
    @DrawableRes val icon: Int = R.drawable.default_icon
)