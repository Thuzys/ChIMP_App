package com.example.chimp.infrastructure

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chimp.models.users.User
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

    @Test
    fun userInfo_emits_null_when_no_user_is_stored() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val user = sut.userInfo.first()
        assert(user == null)
    }

    @Test
    fun update_user_stores_the_user() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val expectedUser = User(1u, "John", "token")
        sut.updateUserInfo(expectedUser)
        val storedUser = sut.userInfo.first()
        assert(storedUser == expectedUser)
    }

    @Test
    fun clear_user_removes_the_user() = runTest {
        val sut = UserInfoPreferencesRepository(cleanDataStoreRule.dataStore)
        val expectedUser = User(1u, "John", "token")
        sut.updateUserInfo(expectedUser)
        sut.clearUserInfo()
        val user = sut.userInfo.first()
        assert(user == null)
    }
}