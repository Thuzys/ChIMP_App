package com.example.chimp.screens.channels.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.channels.model.ChannelsServices

/**
 * The factory for the [ChannelsViewModel].
 *
 * @property service the service used in the ViewModel context.
 * @property userInfoRepository the repository used in the ViewModel context.
 */
@Suppress("UNCHECKED_CAST")
class ChannelsViewModelFactory(
    private val service: ChannelsServices,
    private val userInfoRepository: UserInfoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChannelsViewModel(service, userInfoRepository) as T
    }
}