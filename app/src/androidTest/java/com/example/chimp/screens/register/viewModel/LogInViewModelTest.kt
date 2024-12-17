package com.example.chimp.screens.register.viewModel

import com.example.chimp.screens.register.service.FakeService
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState
import com.example.chimp.services.validation.ChIMPFormValidator
import com.example.chimp.utils.ReplaceMainDispatcherRule

import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Rule
import org.junit.Test

class LogInViewModelTest {

    @get:Rule
    val dispatcherRule = ReplaceMainDispatcherRule()

    @Test
    fun view_model_initial_state_is_login() =
        runTest(dispatcherRule.testDispatcher) {
            // Arrange
            val vm = LoginViewModel(FakeService(), ChIMPFormValidator())

            // Assert
            assert(vm.state.value is RegisterScreenState.LogIn) {
                "Expected LoginScreenState.Login, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_goes_to_success_on_login() =
        runTest(dispatcherRule.testDispatcher) {
            // Arrange
            val service = FakeService()
            val vm = LoginViewModel(service, ChIMPFormValidator())
            // Act
            vm.login(service.validUsername, service.validPassword)
            service.unlock()
            // Assert
            assert(vm.state.value is RegisterScreenState.Success) {
                "Expected LoginScreenState.Success, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_goes_to_register() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val vm = LoginViewModel(FakeService(), ChIMPFormValidator())

        // Act
        vm.toRegister()
        yield()
        assert(vm.state.value is RegisterScreenState.Register) {
            "Expected LoginScreenState.Register, but was ${vm.state.value}"
        }
    }

    @Test
    fun view_model_goes_to_login() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val vm = LoginViewModel(FakeService(), ChIMPFormValidator(),RegisterScreenState.Register())

        // Act
        vm.toLogin()
        yield()
        assert(vm.state.value is RegisterScreenState.LogIn) {
            "Expected LoginScreenState.Login, but was ${vm.state.value}"
        }
    }

    @Test
    fun error_on_login() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val service = FakeService()
        val vm = LoginViewModel(service, ChIMPFormValidator())

        // Act
        vm.login("", "")
        service.unlock()

        // Assert
        assert(vm.state.value is RegisterScreenState.Error) {
            "Expected LoginScreenState.Error, but was ${vm.state.value}"
        }
    }

    @Test
    fun view_model_is_loading() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val service = FakeService()
        val vm = LoginViewModel(service, ChIMPFormValidator())

        // Act
        vm.login("", "")
        yield()
        // Assert
        assert(vm.state.value is RegisterScreenState.Loading) {
            "Expected LoginScreenState.Loading, but was ${vm.state.value}"
        }
        service.unlock()
    }
}