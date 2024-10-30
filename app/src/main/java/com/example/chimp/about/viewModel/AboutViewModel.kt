package com.example.chimp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chimp.about.model.Dev
import com.example.chimp.viewModel.state.AboutScreenState

/**
 * ViewModel for the About screen.
 *
 * @property state the state of the screen
 */
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

////TODO: See video about ViewModelProvider.Factory
//@Suppress("UNCHECKED_CAST")
//class AboutViewModelFactory : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return AboutViewModel() as T
//    }
//}