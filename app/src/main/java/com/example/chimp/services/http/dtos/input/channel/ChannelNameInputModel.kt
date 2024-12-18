package com.example.chimp.services.http.dtos.input.channel

import kotlinx.serialization.Serializable

@Serializable
internal data class ChannelNameInputModel(
    val name: String,
    val displayName: String,
)

