package com.example.chimp.services.http.dtos.output.message

import kotlinx.serialization.Serializable

@Serializable
data class JoinChannelOutputModel(
    val cId: UInt? = null,
    val invitationCode: String? = null
)