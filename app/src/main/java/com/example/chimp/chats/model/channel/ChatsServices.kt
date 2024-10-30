package com.example.chimp.model.chats

import com.example.chimp.model.users.User

interface ChatsServices {
    //TODO: add user in mem
    suspend fun fetchChannels(user: User): List<Channel>
}