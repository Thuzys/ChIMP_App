package com.example.chimp.services.http.dtos.output.message

import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.serialization.Serializable

@Serializable
class ChannelInvitationOutputModel(
    val channelId: UInt,
    val expirationDate: String,
    val maxUses: UInt,
    val accessControl: AccessControl
)