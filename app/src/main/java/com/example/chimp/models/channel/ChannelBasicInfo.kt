package com.example.chimp.models.channel

import androidx.annotation.DrawableRes
import com.example.chimp.R
import com.example.chimp.models.users.UserInfo

data class ChannelBasicInfo(
    val cId: UInt,
    val name: ChannelName,
    val owner: UserInfo,
    @DrawableRes val icon: Int = R.drawable.github_mark,
)
