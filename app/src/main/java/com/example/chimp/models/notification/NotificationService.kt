package com.example.chimp.models.notification

import com.example.chimp.models.either.Either
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.model.messages.Messages
import kotlinx.coroutines.flow.Flow

interface NotificationService {
    suspend fun getNotification(user: User): Either<ResponseError, Flow<List<Messages>>>
    suspend fun clearAllNotification()
    suspend fun clearNotification(message: Messages)
    suspend fun clearNotification(messages: List<Messages>)
}