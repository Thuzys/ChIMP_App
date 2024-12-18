package com.example.chimp.services.dummy

import com.example.chimp.R
import com.example.chimp.models.either.Either
import com.example.chimp.models.either.success
import com.example.chimp.screens.chats.model.channel.ChannelName
import com.example.chimp.screens.findChannel.model.FindChannelItem
import com.example.chimp.screens.findChannel.model.FindChannelService
import com.example.chimp.models.errors.ResponseErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DummyFindChannelService: FindChannelService {

    private val channels = mutableListOf(
        FindChannelItem(
            cId = 1u,
            name = ChannelName("brgm channel"),
            icon = R.drawable.brgm_profile_pic,
        ),
        FindChannelItem(
            cId = 2u,
            name = ChannelName("thuzy channel"),
            icon = R.drawable.thuzy_profile_pic,
        ),
        FindChannelItem(
            cId = 3u,
            name = ChannelName("github community"),
            icon = R.drawable.github_mark,
        ),
    )

    override suspend fun joinChannel(channelId: UInt, invitationCode: String?): Either<ResponseErrors, Unit> {
        return success(Unit)
    }

    override suspend fun findChannelByName(channelName: ChannelName): Either<ResponseErrors, FindChannelItem?> {
        val channel =  channels.find { it.name == channelName }
        return success(channel)
    }

    override suspend fun findChannelsByPartialName(channelName: ChannelName): Either<ResponseErrors, Flow<List<FindChannelItem>>> {
        val filteredChannels = channels.filter { it.name.name.contains(channelName.name, ignoreCase = true) }
        return success(flowOf(filteredChannels))
    }

    override suspend fun getChannels(
        offset: UInt?,
        limit: UInt?
    ): Either<ResponseErrors, Flow<List<FindChannelItem>>> {
        return success(flowOf(channels))
    }
}