package com.example.chimp.model.chats

data class ChannelName(
    private val name: String
) {
    init {
        require(name.isNotBlank()) { "Channel name cannot be blank" }
    }
}
