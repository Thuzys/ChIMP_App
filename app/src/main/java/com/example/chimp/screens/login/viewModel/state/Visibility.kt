package com.example.chimp.screens.login.viewModel.state

/**
 * A sealed interface that represents the visibility of [Register] and [Login].
 */
sealed interface Visibility {
    interface Show : Visibility
    interface Hide : Visibility
}