package com.example.chimp.screens.findChannel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.screens.findChannel.model.FindChannelService

/**
 * The factory for the [FindChannelViewModel].
 *
 * @property service the service used in the ViewModel context.
 */
@Suppress("UNCHECKED_CAST")
class FindChannelVMFactory(private val service: FindChannelService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FindChannelViewModel(service) as T
    }

}