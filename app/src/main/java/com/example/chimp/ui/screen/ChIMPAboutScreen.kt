package com.example.chimp.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.chimp.ui.view.about.IdleAboutDevView
import com.example.chimp.ui.view.about.ShowDialogAboutDevView
import com.example.chimp.ui.view.about.ShowingAboutDevView
import com.example.chimp.viewModel.state.AboutScreenState
import com.example.chimp.viewModel.AboutViewModel

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