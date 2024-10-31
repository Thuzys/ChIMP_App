package com.example.chimp.chats.model.channel

import com.example.chimp.chats.model.users.User

interface ChatsServices {
    //TODO: add user in mem
    suspend fun fetchChannels(user: User): List<Channel>
}