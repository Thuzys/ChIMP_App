package com.example.chimp.models.users

/**
 * User info
 *
 * @param id User id
 * @param name User name
 */
data class UserInfo(
    val id: UInt,
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "User name cannot be blank" }
    }

    fun toPreferences(): String {
        return """$id#$name""".trimIndent()
    }

    companion object {
        fun fromPreferences(preferences: String): UserInfo {
            try {
                val (id, name) = preferences.split("#", limit = 2)
                return UserInfo(id.toUInt(), name)
            } catch (e: IndexOutOfBoundsException) {
                throw IllegalArgumentException("Invalid preferences string")
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid preferences string")
            }
        }
    }
}