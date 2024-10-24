package com.example.chimp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chimp.ui.view.community.LoadingCommunityView
import com.example.chimp.viewModel.CommunityViewModel
import com.example.chimp.viewModel.state.CommunityScreenState

@Composable
fun ChIMPCommunityScreen(
    modifier: Modifier = Modifier,
    viewModel: CommunityViewModel
) {
    when (val state = viewModel.state) {
        is CommunityScreenState.Loading -> {
            LoadingCommunityView()
        }

        is CommunityScreenState.Error -> TODO()
        is CommunityScreenState.Loaded -> TODO()
    }
}