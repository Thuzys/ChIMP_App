package com.example.chimp.services.http.dtos.input.channel

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.toIcon
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.serialization.Serializable

@Serializable
internal data class ChannelListInputModel(
    val name: ChannelNameInputModel,
    val id: UInt,
    val owner: OwnerInputModel,
    val description: String,
    val icon: String,
    val accessControl: String,
)

internal fun List<ChannelListInputModel>.toChannelInfo(): List<ChannelInfo> {
    return map {
        ChannelInfo(
            cId = it.id,
            name = ChannelName(it.name.name, it.name.displayName),
            icon = it.icon.toIcon(),
            owner = UserInfo(it.owner.id, it.owner.name),
            accessControl = AccessControl.valueOf(it.accessControl),
            description = it.description.ifBlank { null }
        )
    }
}