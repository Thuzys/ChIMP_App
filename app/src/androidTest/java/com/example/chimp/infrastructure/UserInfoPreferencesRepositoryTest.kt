package com.example.chimp.infrastructure

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import com.example.chimp.models.users.UserInfo
import com.example.chimp.utils.CleanDataStoreRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserInfoPreferencesRepositoryTest {
    @get:Rule
    val cleanDataStoreRule = CleanDataStoreRule()

    private val token = Token("token")

    @Test
    fun userInfo_emits_null_when_no_user_is_stored() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val user = sut.userInfo.first()
        assert(user == null)
    }

    @Test
    fun update_user_stores_the_user() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val expectedUser = User(1u, "John", token)
        sut.updateUserInfo(expectedUser)
        val storedUser = sut.userInfo.first()
        assert(storedUser == expectedUser)
    }

    @Test
    fun clear_user_removes_the_user() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val expectedUser = User(1u, "John", token)
        sut.updateUserInfo(expectedUser)
        sut.clearUserInfo()
        val user = sut.userInfo.first()
        assert(user == null)
    }


    @Test
    fun when_channel_list_is_null_then_channel_list_emits_empty_list() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val channelList = sut.channelList.first()
        assert(channelList.isEmpty())
    }

    @Test
    fun update_channel_list_stores_the_channel_list() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        var cId = 1u
        val channelName = ChannelName("@User1/Channel1", "Channel1")
        val owner = UserInfo(1u, "User1")
        val channelList = listOf(
            ChannelInfo(
                cId = cId++,
                name = channelName,
                owner = owner
            ),
            ChannelInfo(
                cId = cId,
                name = channelName,
                owner = owner
            )
        )
        sut.updateChannelList(channelList)
        val storedChannelList = sut.channelList.first()
        assert(storedChannelList.size == channelList.size) {
            "Expected: ${channelList.size}, Actual: ${storedChannelList.size}"
        }
    }
}