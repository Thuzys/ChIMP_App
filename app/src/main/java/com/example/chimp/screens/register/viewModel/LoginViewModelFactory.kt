package com.example.chimp.screens.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.screens.register.model.RegisterService

/**
 * The factory for the [LoginViewModel].
 *
 * @property service the service used in the ViewModel context.
 * @property formValidation the form validation used in the ViewModel context.
 */
@Suppress("UNCHECKED_CAST")
internal class LoginViewModelFactory(
    private val service: RegisterService,
    private val formValidation: FormValidation
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(service, formValidation) as T
    }
}