package com.example.chimp.findChannel.model

sealed interface FindChannelErrors {
    /**
     * The error when joining a channel.
     */
    data class JoinError(val message: String) : FindChannelErrors
}