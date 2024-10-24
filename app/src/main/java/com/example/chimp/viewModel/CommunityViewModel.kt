package com.example.chimp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chimp.viewModel.state.CommunityScreenState

class CommunityViewModel: ViewModel() {
    var state: CommunityScreenState by mutableStateOf(CommunityScreenState.Loading)
        private set
}