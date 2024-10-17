package com.example.chimp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chimp.model.dev.Dev
import com.example.chimp.viewModel.state.AboutScreenState

class AboutViewModel : ViewModel() {
    var state: AboutScreenState by mutableStateOf(AboutScreenState.Idle)
        private set

    fun showDev(dev: Dev) {
        if (state !is AboutScreenState.Showing || (state as AboutScreenState.Showing).dev != dev)
            state = AboutScreenState.Showing(dev)
    }

    fun showDialog(dev: Dev) {
        if (state !is AboutScreenState.ShowDialog)
            state = AboutScreenState.ShowDialog(dev)
    }

    fun idle() {
        if (state !is AboutScreenState.Idle)
            state = AboutScreenState.Idle
    }

}