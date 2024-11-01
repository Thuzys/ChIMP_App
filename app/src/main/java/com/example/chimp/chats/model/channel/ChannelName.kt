package com.example.chimp.chats.model.channel

@JvmInline
value class ChannelName(
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "Channel name cannot be blank" }
    }
}
