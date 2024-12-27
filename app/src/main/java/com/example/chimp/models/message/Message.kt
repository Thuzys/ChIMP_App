package com.example.chimp.models.message

import com.example.chimp.models.users.User
import com.example.chimp.models.users.UserInfo
import java.sql.Timestamp

data class Message(
    val owner: UserInfo,
    val message: String,
    val time: Timestamp? = null,
    val mId: UInt? = null,
    val cId: UInt,
) {
    init {
        require(message.isNotBlank()) { "Message cannot be blank" }
    }

    fun toPreferences(): String {
        return """
            ${owner.toPreferences()}#$message#$time#$mId#$cId
        """.trimIndent()
    }

    companion object {
        fun fromPreferences(preferences: String): Message {
            try {
                val params = preferences.split("#", limit = 6)
                return Message(
                    owner = UserInfo(params[0].toUInt(), params[1]),
                    message = params[2],
                    time = if (params[3] !== "null") Timestamp.valueOf(params[3]) else null,
                    mId = params[4].toUInt(),
                    cId = params[5].toUInt()
                )
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalArgumentException("Invalid preferences string")
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid preferences string")
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid preferences string")
            }
        }
    }
}
