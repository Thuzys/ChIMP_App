package com.example.chimp.findChannel.model

/**
 * Interface that defines the service used in FindChannelViewModel.
 */
interface FindChannelService {
    /**
     * Join a channel.
     */
    fun joinChannel(channelId: String, userId: String, callback: (FindChannelErrors?) -> Unit)
}