package com.example.chimp.screens.createChannel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.createChannel.model.CreateChannelService

@Suppress("UNCHECKED_CAST")
class CreateChannelVMFactory(
    private val service: CreateChannelService,
    private val userInfoRepository: UserInfoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateChannelViewModel(service, userInfoRepository) as T
    }
}
