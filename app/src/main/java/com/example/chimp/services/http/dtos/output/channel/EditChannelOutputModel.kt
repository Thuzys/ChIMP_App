package com.example.chimp.services.http.dtos.output.channel

import com.example.chimp.models.channel.ChannelInfo
import kotlinx.serialization.Serializable
import com.example.chimp.models.toStrIcon

@Serializable
data class EditChannelOutputModel(
    val name: String,
    val visibility: String,
    val description: String,
    val icon: String,
) {
    companion object {
        fun fromChannelInfo(channel: ChannelInfo) =
            EditChannelOutputModel(
                channel.name.displayName,
                channel.visibility.name,
                channel.description ?: "",
                channel.icon.toStrIcon()
            )
    }
}
