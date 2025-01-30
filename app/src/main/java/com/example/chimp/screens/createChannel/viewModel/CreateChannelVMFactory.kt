package com.example.chimp.screens.createChannel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.models.repository.ChannelRepository
import com.example.chimp.screens.createChannel.model.CreateChannelService

@Suppress("UNCHECKED_CAST")
class CreateChannelVMFactory(
    private val service: CreateChannelService,
    private val channelRepository: ChannelRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateChannelViewModel(service, channelRepository) as T
    }
}
