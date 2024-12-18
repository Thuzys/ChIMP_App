package com.example.chimp.services.http.dtos.input.channel

import com.example.chimp.models.channel.ChannelBasicInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.toIcon
import com.example.chimp.models.users.UserInfo
import kotlinx.serialization.Serializable

@Serializable
internal data class ChannelListInputModel(
    val name: ChannelNameInputModel,
    val id: UInt,
    val owner: OwnerInputModel,
    val description: String,
    val icon: String,
)

internal fun List<ChannelListInputModel>.toChannelInfo(): List<ChannelBasicInfo> {
    return map {
        ChannelBasicInfo(
            cId = it.id,
            name = ChannelName(it.name.displayName),
            icon = it.icon.toIcon(),
            owner = UserInfo(it.owner.id, it.owner.name),
        )
    }
}