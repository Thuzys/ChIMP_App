package com.example.chimp.services.http.dtos.output.channel

import com.example.chimp.models.channel.AccessControl
import com.example.chimp.models.channel.Visibility
import kotlinx.serialization.Serializable

@Serializable
data class CreateChannelOutputModel (
    val owner: UInt,
    val name: String,
    val visibility: Visibility,
    val accessControl: AccessControl,
    val description: String?,
    val icon: String
)