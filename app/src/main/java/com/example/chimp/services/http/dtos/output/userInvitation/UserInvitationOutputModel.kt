package com.example.chimp.services.http.dtos.output.userInvitation

import kotlinx.serialization.Serializable

@Serializable
data class UserInvitationOutputModel(
    val expirationDate: String,
)
