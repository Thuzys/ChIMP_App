package com.example.chimp.services.http.dtos.input.channel

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.toIcon
import com.example.chimp.models.users.UserInfo
import com.example.chimp.models.channel.ChannelInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class ChannelInputModel(
    val id: UInt,
    val name: ChannelNameInputModel,
    val description: String?,
    val icon: String,
    val visibility: String,
    val owner: OwnerInputModel
) {
    fun toChannelInfo() = ChannelInfo(
        cId = id,
        name = ChannelName(name.name, name.displayName),
        description = if (description == "") null else description,
        icon = icon.toIcon(),
        owner = UserInfo(owner.id, owner.name)
    )

    fun toChannelBasicInfo() = ChannelBasicInfo(
        cId = id,
        name = ChannelName(name.name, name.displayName),
        owner = UserInfo(owner.id, owner.name),
        icon = icon.toIcon()
    )
}
