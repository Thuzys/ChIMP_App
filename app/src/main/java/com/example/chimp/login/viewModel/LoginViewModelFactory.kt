package com.example.chimp.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.login.model.LoginService

/**
 * The factory for the [LoginViewModel].
 *
 * @property service the service used in the ViewModel context.
 */
@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val service: LoginService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(service) as T
    }
}