package com.example.chimp.models.channel

import com.example.chimp.screens.channel.model.accessControl.AccessControl
import java.sql.Timestamp
import java.util.Calendar

data class ChannelInvitation(
    val expiresAfter: Timestamp,
    val maxUses: UInt,
    val permission: AccessControl,
): Invitation {
    companion object {
        fun createDefault(): ChannelInvitation {
            return ChannelInvitation(
                expiresAfter = Timestamp(30 * 60 * 1000L),
                maxUses = 1u,
                permission = AccessControl.READ_WRITE
            )
        }
    }

    fun getExpirationTime(): Timestamp {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis() + expiresAfter.time
        return Timestamp(cal.timeInMillis)
    }
}



