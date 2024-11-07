package com.example.chimp.screens.about.viewModel

import com.example.chimp.screens.about.viewModel.state.AboutScreenState
import org.junit.Test

class AboutViewModelTest {

    @Test
    fun toShowingStateTest() {
        val viewModel = AboutViewModel()
        viewModel.showDev(AboutScreenState.devs.first())
        assert(viewModel.state is AboutScreenState.Showing)
    }

    @Test
    fun toDialogStateTest() {
        val viewModel = AboutViewModel()
        viewModel.showDialog(AboutScreenState.devs.first())
        assert(viewModel.state is AboutScreenState.ShowDialog)
    }

    @Test
    fun toIdleStateTest() {
        val viewModel = AboutViewModel()
        viewModel.showDev(AboutScreenState.devs.first())
        assert(viewModel.state is AboutScreenState.Showing)
        viewModel.idle()
        assert(viewModel.state is AboutScreenState.Idle)
    }

    @Test
    fun initialStateTest() {
        val viewModel = AboutViewModel()
        assert(viewModel.state is AboutScreenState.Idle)
    }
}