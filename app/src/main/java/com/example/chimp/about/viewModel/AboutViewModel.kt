package com.example.chimp.about.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chimp.about.model.Dev
import com.example.chimp.about.viewModel.state.AboutScreenState

/**
 * ViewModel for the About screen.
 *
 * This ViewModel is responsible for managing the state of the About screen.
 *
 * @property state the state of the screen
 */
class AboutViewModel : ViewModel() {
    var state: AboutScreenState by mutableStateOf(AboutScreenState.Idle)
        private set

    fun showDev(dev: Dev) {
        val currState = state
        if (currState !is AboutScreenState.Showing || currState.dev != dev)
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
