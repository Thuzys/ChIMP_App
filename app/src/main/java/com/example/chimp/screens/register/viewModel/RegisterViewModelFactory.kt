package com.example.chimp.screens.register.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chimp.models.repository.UserInfoRepository
import com.example.chimp.screens.register.model.FormValidation
import com.example.chimp.screens.register.model.RegisterService

/**
 * The factory for the [RegisterViewModel].
 *
 * @property service the service used in the ViewModel context.
 * @property formValidation the form validation used in the ViewModel context.
 * @property userInfoRepository the repository used in the ViewModel context.
 */
@Suppress("UNCHECKED_CAST")
internal class RegisterViewModelFactory(
    private val service: RegisterService,
    private val formValidation: FormValidation,
    private val userInfoRepository: UserInfoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(service, formValidation, userInfoRepository) as T
    }
}