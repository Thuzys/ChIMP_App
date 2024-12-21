package com.example.chimp.utils.repository

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.models.users.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * A JUnit rule that provides a fake [UserInfoRepository] for testing.
 *
 * @property repo The fake [UserInfoRepository] to be used in tests.
 *
 * @see TestRule
 */
class FakeUserInfoRepositoryRule : TestRule {

    val repo = object : UserInfoRepository {
        private val store = MutableStateFlow<User?>(null)

        override val userInfo: Flow<User?> = store
        override val channelList: Flow<List<ChannelInfo>>
            get() = TODO("Not yet implemented")

        override suspend fun updateUserInfo(user: User) = store.emit(user)
        override suspend fun updateChannelList(channelList: List<ChannelInfo>) {
            TODO("Not yet implemented")
        }

        override suspend fun clearUserInfo() = store.emit(null)

    }

    override fun apply(test: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() = runTest {
                try {
                    test.evaluate()
                } finally {
                    repo.clearUserInfo()
                }
            }
        }
}