package com.example.chimp.services.http.dtos.input.userInvitation

import kotlinx.serialization.Serializable

@Serializable
data class UserInvitationInputModel(
    val expirationDate: String,
    val invitationCode: String,
)