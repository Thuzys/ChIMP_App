package com.example.chimp.models.channel

data class ChannelName(
    val name: String,
    val displayName: String,
) {
    init {
        require(name.isNotBlank()) { "Channel name cannot be blank" }
        require(displayName.isNotBlank()) { "Channel display name cannot be blank" }
    }

    fun toPreferences(): String {
        return """
            $name
            $displayName
        """.trimIndent()
    }

    companion object {
        fun fromPreferences(preferences: String): ChannelName {
            val lines = preferences.lines()
            require(lines.size == 2) { "Invalid preferences string" }
            return ChannelName(
                displayName = lines[0],
                name = lines[1]
            )
        }
    }
}
