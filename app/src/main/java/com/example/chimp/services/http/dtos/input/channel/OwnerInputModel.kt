package com.example.chimp.services.http.dtos.input.channel

import kotlinx.serialization.Serializable

@Serializable
internal data class OwnerInputModel(
    val id: UInt,
    val name: String,
)
