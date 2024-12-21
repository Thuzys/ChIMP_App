package com.example.chimp.infrastructure

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.message.Message
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.channel.model.accessControl.AccessControl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ChannelPreferencesRepository(
    private val store: DataStore<Preferences>
) : ChannelRepository {
    override val channelInfo: Flow<ChannelInfo?>
        = store.data.map(Preferences::toChannelInfo)

    override suspend fun updateChannelInfo(channel: ChannelInfo) {
        store.edit(transform = channel::writeToPreferences)
    }

    override suspend fun clearChannelInfo() {
        store.edit(MutablePreferences::clear)
    }

    override suspend fun fetchChannelMessages(channel: ChannelInfo): List<Message> {
        val key = stringPreferencesKey(channel.name.name)
        val d = store.data.map { value: Preferences -> value[key] }.first() ?: return emptyList()
        return d.lines().filter(String::isNotBlank).map { Message.fromPreferences(it) }
    }

    override suspend fun saveMessages(messages: List<Message>) {
        val channel = channelInfo.first() ?: return
        val key = stringPreferencesKey(channel.name.name)
        store.edit { preferences ->
            preferences[key] = messages.joinToString("\n", transform = Message::toPreferences)
        }
    }
}

private val channelIdKey = stringPreferencesKey("channel_id")
private val channelNameKey = stringPreferencesKey("channel_name")
private val channelOwnerKey = stringPreferencesKey("channel_owner")
private val channelIconKey = stringPreferencesKey("channel_icon")
private val channelAccessControlKey = stringPreferencesKey("channel_access_control")
private val channelDescriptionKey = stringPreferencesKey("channel_description")

private fun  ChannelInfo.writeToPreferences(preferences: MutablePreferences) {
    preferences[channelIdKey] = cId.toString()
    preferences[channelNameKey] = name.toPreferences()
    preferences[channelOwnerKey] = owner.toPreferences()
    preferences[channelIconKey] = icon.toString()
    preferences[channelAccessControlKey] = accessControl.toString()
    preferences[channelDescriptionKey] = description ?: "null"
}

private fun Preferences.toChannelInfo(): ChannelInfo? {
    val channelId = this[channelIdKey]?.toUIntOrNull() ?: return null
    val channelName = this[channelNameKey] ?: return null
    val channelOwner = this[channelOwnerKey] ?: return null
    val channelIcon = this[channelIconKey]?.toIntOrNull() ?: return null
    val channelAccessControl = this[channelAccessControlKey]?.let { AccessControl.valueOf(it) } ?: return null
    val channelDescription = this[channelDescriptionKey]?.let { if (it == "null") null else it }
    return ChannelInfo(
        cId = channelId,
        name = ChannelName.fromPreferences(channelName),
        owner = UserInfo.fromPreferences(channelOwner),
        icon = channelIcon,
        accessControl = channelAccessControl,
        description = channelDescription
    )
}


