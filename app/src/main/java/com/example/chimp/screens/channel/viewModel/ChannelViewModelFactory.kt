package com.example.chimp.screens.channel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.channel.model.ChannelService

/**
 * The factory for the [ChannelViewModel].
 *
 * @property service the service used in the ViewModel context.
 * @property userInfoRepo the repository used in the ViewModel context.
 * @property channelRepo the repository used in the ViewModel context.
 */
@Suppress("UNCHECKED_CAST")
class ChannelViewModelFactory(
    private val service: ChannelService,
    private val userInfoRepo: UserInfoRepository,
    private val channelRepo: ChannelRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChannelViewModel(channelRepo, service, userInfoRepo) as T
    }
}