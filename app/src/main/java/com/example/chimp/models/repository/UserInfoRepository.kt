package com.example.chimp.models.repository

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.users.User
import kotlinx.coroutines.flow.Flow

/**
 * Characterizes the repository for user information,
 * responsible for regulating access to it.
 */
interface UserInfoRepository {
    /**
     * A flow of the user information.
     */
    val userInfo: Flow<User?>

    /**
     * A flow of the channel list.
     */
    val channelList: Flow<List<ChannelInfo>>

    /**
     * Updates the user information.
     */
    suspend fun updateUserInfo(user: User)

    /**
     * Updates the channel list.
     */
    suspend fun updateChannelList(channelList: List<ChannelInfo>)

    /**
     * Clears the user information.
     */
    suspend fun clearUserInfo()
}