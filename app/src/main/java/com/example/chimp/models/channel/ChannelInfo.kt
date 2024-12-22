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
) {
    fun toChannelInfoString(): String =
        "$cId:${name.name}:${name.displayName}:${description}:${owner.id}:${owner.name}:${visibility}:${accessControl}:${icon}"
}

fun List<ChannelInfo>.toChannelInfoListString(): String {
    return this.joinToString(",", transform = ChannelInfo::toChannelInfoString)
}

fun String.toChannelInfo(): ChannelInfo {
    val parts = this.split(":")
    return ChannelInfo(
        cId = parts[0].toUInt(),
        name = ChannelName(parts[1], parts[2]),
        description = parts[3],
        owner = UserInfo(parts[4].toUInt(), parts[5]),
        visibility = Visibility.valueOf(parts[6]),
        accessControl = AccessControl.valueOf(parts[7]),
        icon = parts[8].toInt()
    )
}

fun String.toChannelInfoList(): List<ChannelInfo> {
    return this.split(",").map { it.toChannelInfo() }
}