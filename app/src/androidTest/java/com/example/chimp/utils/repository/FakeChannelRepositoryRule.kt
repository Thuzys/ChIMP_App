package com.example.chimp.utils.repository

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.message.Message
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.users.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.sql.Timestamp

class FakeChannelRepositoryRule : TestRule {

    val repo = object : ChannelRepository {
        override val channelInfo = MutableStateFlow<ChannelInfo?>(null)

        override suspend fun updateChannelInfo(channel: ChannelInfo) = channelInfo.emit(channel)

        override suspend fun clearChannelInfo() = channelInfo.emit(null)

        override suspend fun fetchChannelMessages(channel: ChannelInfo): List<Message> =
            when (channel) {
                channelWithMessages -> listOf(
                    Message(
                        mId = 1u,
                        message = "Message1",
                        time = Timestamp(System.currentTimeMillis() + 3600000L),
                        cId = channelWithMessages.cId,
                        owner = channelWithMessages.owner
                    ),
                    Message(
                        mId = 2u,
                        message = "Message2",
                        time = Timestamp(System.currentTimeMillis() + 7200000L),
                        cId = channelWithMessages.cId,
                        owner = channelWithMessages.owner
                    )
                )
                else -> emptyList()
            }

        override suspend fun saveMessages(messages: List<Message>) {
            // Do nothing
        }
    }

    companion object {
        val emptyChannel = ChannelInfo(
            cId = 1u,
            name = ChannelName("Channel1", "Channel1"),
            owner = UserInfo(1u, "Owner1")
        )

        val channelWithMessages = ChannelInfo(
            cId = 2u,
            name = ChannelName("Channel2", "Channel2"),
            owner = UserInfo(2u, "Owner2")
        )
    }

    override fun apply(base: Statement, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() = runTest {
                try {
                    base.evaluate()
                } finally {
                    repo.clearChannelInfo()
                }
            }
        }
}