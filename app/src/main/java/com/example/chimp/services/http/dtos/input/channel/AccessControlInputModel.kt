package com.example.chimp.services.http.dtos.input.channel

import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.serialization.Serializable

/**
 * Input model for access control.
 *
 * @property accessControl the access control.
 */
@Serializable
internal data class AccessControlInputModel(
    val accessControl: String,
) {
    fun toAccessControl(): AccessControl {
        return AccessControl.valueOf(accessControl)
    }
}
