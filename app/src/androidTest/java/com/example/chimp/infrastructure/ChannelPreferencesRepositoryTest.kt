package com.example.chimp.infrastructure

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.message.Message
import com.example.chimp.models.users.UserInfo
import com.example.chimp.utils.CleanDataStoreRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.sql.Timestamp

@RunWith(AndroidJUnit4::class)
class ChannelPreferencesRepositoryTest {
    @get:Rule
    val cleanDataStoreRule = CleanDataStoreRule()

    @Test
    fun initially_channel_info_is_null() = runTest {
        val sut = ChannelPreferencesRepository(cleanDataStoreRule.dataStore)
        val channelInfo = sut.channelInfo.first()
        assert(channelInfo == null)
    }

    @Test
    fun update_channel_info_stores_the_channel_info() = runTest {
        val sut = ChannelPreferencesRepository(cleanDataStoreRule.dataStore)
        val channelName = ChannelName("channel", "channel")
        val owner = UserInfo(1u, "owner")
        val expectedChannelInfo = ChannelInfo(
            cId = 1u,
            name = channelName,
            owner = owner,
        )
        sut.updateChannelInfo(expectedChannelInfo)
        val storedChannelInfo = sut.channelInfo.first()
        assert(storedChannelInfo == expectedChannelInfo)
    }

    @Test
    fun clear_channel_info_removes_the_channel_info() = runTest {
        val sut = ChannelPreferencesRepository(cleanDataStoreRule.dataStore)
        val channelName = ChannelName("channel", "channel")
        val owner = UserInfo(1u, "owner")
        val expectedChannelInfo = ChannelInfo(
            cId = 1u,
            name = channelName,
            owner = owner,
        )
        sut.updateChannelInfo(expectedChannelInfo)
        sut.clearChannelInfo()
        val channelInfo = sut.channelInfo.first()
        assert(channelInfo == null)
    }

    @Test
    fun initially_the_messages_channels_message_is_empty() = runTest {
        val sut = ChannelPreferencesRepository(cleanDataStoreRule.dataStore)
        val channelName = ChannelName("channel", "channel")
        val owner = UserInfo(1u, "owner")
        val channelInfo = ChannelInfo(
            cId = 1u,
            name = channelName,
            owner = owner,
        )
        val messages = sut.fetchChannelMessages(channelInfo)
        assert(messages.isEmpty())
    }

    @Test
    fun save_messages_stores_the_messages() = runTest {
        val sut = ChannelPreferencesRepository(cleanDataStoreRule.dataStore)
        val channelName = ChannelName("channel", "channel")
        val owner = UserInfo(1u, "owner")
        val channelInfo = ChannelInfo(
            cId = 1u,
            name = channelName,
            owner = owner,
        )
        val messages = List(5) {
            Message(
                owner = owner,
                mId = it.toUInt(),
                message = "message$it",
                time = Timestamp(System.currentTimeMillis()),
                cId = 1u
            )
        }
        sut.updateChannelInfo(channelInfo)
        sut.saveMessages(messages)
        val storedMessages = sut.fetchChannelMessages(channelInfo)
        assert(storedMessages.size == messages.size) {
            "Expected: ${messages.size}, Actual: ${storedMessages.size}"
        }
    }
}