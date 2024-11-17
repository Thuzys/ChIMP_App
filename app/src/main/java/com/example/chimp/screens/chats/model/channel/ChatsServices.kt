package com.example.chimp.screens.chats.model.channel

interface ChatsServices {
    //TODO: add user in mem
    suspend fun fetchChannels(user: com.example.chimp.screens.chats.model.users.User): List<Channel>
}