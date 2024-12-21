package com.example.chimp.models.repository

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.message.Message
import kotlinx.coroutines.flow.Flow

/**
 * Characterizes the repository for channel information,
 * responsible for regulating access to it.
 */
interface ChannelRepository {
    /**
     * A flow of the channel information.
     */
    val channelInfo: Flow<ChannelInfo?>

    /**
     * Updates the channel information.
     */
    suspend fun updateChannelInfo(channel: ChannelInfo)

    /**
     * Clears the channel information.
     */
    suspend fun clearChannelInfo()

    /**
     * Fetches the channel messages.
     */
    suspend fun fetchChannelMessages(channel: ChannelInfo): List<Message>

    /**
     * Saves the channel messages.
     */
    suspend fun saveMessages(messages: List<Message>)
}