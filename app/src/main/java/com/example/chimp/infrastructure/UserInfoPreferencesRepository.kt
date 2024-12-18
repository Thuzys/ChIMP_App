package com.example.chimp.infrastructure

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.Timestamp

/**
 * A [UserInfoRepository] implementation that stores user info in a [DataStore].
 *
 * @property store The data store.
 */
class UserInfoPreferencesRepository(
    private val store: DataStore<Preferences>
) : UserInfoRepository {
    override val userInfo: Flow<User?> = store.data.map(Preferences::toUser)

    override suspend fun updateUserInfo(user: User) {
        store.edit(transform = user::writeToPreferences)
    }

    override suspend fun clearUserInfo() {
        store.edit(MutablePreferences::clear)
    }
}

/**
 * The userId key.
 */
private val userIdKey = stringPreferencesKey("userid")

/**
 * The username key.
 */
private val usernameKey = stringPreferencesKey("username")

/**
 * The token key.
 */
private val tokenKey = stringPreferencesKey("token")
private val tokenTimestampKey = stringPreferencesKey("token_timestamp")

/**
 * Utility functions for converting between User and Preferences.
 *
 * @receiver The preferences.
 * @return The user.
 */
private fun Preferences.toUser(): User? {
    val userId = this[userIdKey]?.toUIntOrNull() ?: return null
    val username = this[usernameKey] ?: return null
    val tokenValue = this[tokenKey] ?: return null
    val tokenTimestamp = this[tokenTimestampKey] ?: return null
    val token = Token(tokenValue, Timestamp.valueOf(tokenTimestamp))
    return User(userId, username, token)
}

/**
 * Writes the user to the preferences.
 *
 * @receiver The user.
 * @param preferences The preferences.
 */
private fun User.writeToPreferences(preferences: MutablePreferences) {
    preferences[userIdKey] = id.toString()
    preferences[usernameKey] = name
    preferences[tokenKey] = token.token
    preferences[tokenTimestampKey] = token.expirationDate.toString()
}