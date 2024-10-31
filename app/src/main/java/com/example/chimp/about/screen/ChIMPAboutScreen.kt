package com.example.chimp.about.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.about.screen.view.IdleAboutDevView
import com.example.chimp.about.screen.view.ShowDialogAboutDevView
import com.example.chimp.about.screen.view.ShowingAboutDevView
import com.example.chimp.about.viewModel.AboutViewModel
import com.example.chimp.about.viewModel.state.AboutScreenState

@Composable
fun ChIMPAboutScreen(
    modifier: Modifier = Modifier,
    viewModel: AboutViewModel
) {
    when (val state = viewModel.state) {
        is AboutScreenState.Idle -> {
            IdleAboutDevView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                onIsExpandedChange = viewModel::showDev
            )
        }
        is AboutScreenState.Showing -> {
            ShowingAboutDevView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                state = state,
                onIdleChange = viewModel::idle,
                onShowDialogChange = viewModel::showDialog,
                onIsExpandedChange = viewModel::showDev
            )
        }
        is AboutScreenState.ShowDialog -> {
            ShowDialogAboutDevView(
                modifier = modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                state = state,
                onShowingChange = viewModel::showDev,
            )
        }
    }
}
