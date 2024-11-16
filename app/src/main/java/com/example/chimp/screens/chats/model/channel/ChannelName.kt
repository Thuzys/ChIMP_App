package com.example.chimp.screens.chats.model.channel

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@JvmInline
value class ChannelName(
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "Channel name cannot be blank" }
    }

    fun encode(): String = URLEncoder.encode(name, StandardCharsets.UTF_8.toString())
}
