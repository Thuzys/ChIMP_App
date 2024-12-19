package com.example.chimp.models.repository

import com.example.chimp.models.channel.ChannelBasicInfo
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
    val channelInfo: Flow<ChannelBasicInfo?>

    /**
     * Updates the channel information.
     */
    suspend fun updateChannelInfo(channel: ChannelBasicInfo)

    /**
     * Clears the channel information.
     */
    suspend fun clearChannelInfo()

    /**
     * Fetches the channel messages.
     */
    suspend fun fetchChannelMessages(channel: ChannelBasicInfo): List<Message>

    /**
     * Saves the channel messages.
     */
    suspend fun saveMessages(messages: List<Message>)
}