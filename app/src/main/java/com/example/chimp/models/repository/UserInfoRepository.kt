package com.example.chimp.models.repository

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
     * Retrieves the user information.
     */
    suspend fun getUserInfo(): User?

    /**
     * Updates the user information.
     */
    suspend fun updateUserInfo(user: User)

    /**
     * Clears the user information.
     */
    suspend fun clearUserInfo()
}