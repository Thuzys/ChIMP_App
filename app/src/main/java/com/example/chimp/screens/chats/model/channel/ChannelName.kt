package com.example.chimp.screens.chats.model.channel

data class ChannelName(
    val name: String,
) {
    init {
        require(name.isNotBlank()) { "Channel name cannot be blank" }
    }
    val fullName: String = name
}
