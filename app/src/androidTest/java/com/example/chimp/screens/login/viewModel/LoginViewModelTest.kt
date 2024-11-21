package com.example.chimp.screens.login.viewModel

import com.example.chimp.screens.login.service.FakeService
import com.example.chimp.screens.login.viewModel.state.LoginScreenState
import com.example.chimp.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val dispatcherRule = ReplaceMainDispatcherRule()

    @Test
    fun testViewModelInitialState() =
        runTest(dispatcherRule.testDispatcher) {
            // Arrange
            val vm = LoginViewModel(FakeService())

            // Act
            var state: LoginScreenState? = null
            vm.state.take(1).collect {
                state = it
            }

            // Assert
            assert(state is LoginScreenState.Login) {
                "Expected LoginScreenState.Login, but was $state"
            }
        }

    @Test
    fun testViewModelLogin() =
        runTest(dispatcherRule.testDispatcher) {
            // Arrange
            val service = FakeService()
            val vm = LoginViewModel(service)
            // Act
            vm.login(service.validUsername, service.validPassword)
            service.unlock()
            // Assert
            var state: LoginScreenState? = null
            vm.state.take(1).collect {
                state = it
            }
            assert(state is LoginScreenState.Success) {
                "Expected LoginScreenState.Success, but was $state"
            }
        }

    @Test
    fun testToRegisterTransition() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val vm = LoginViewModel(FakeService())

        // Act
        vm.toRegister("", "")

        delay(50)
        // Assert
        var state: LoginScreenState? = null
        vm.state.take(1).collect {
            state = it
        }
        assert(state is LoginScreenState.Register) {
            "Expected LoginScreenState.Register, but was $state"
        }
    }

    @Test
    fun testToLoginTransition() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val vm = LoginViewModel(FakeService(), LoginScreenState.Register())

        // Act
        vm.toLogin("", "")
        delay(50)
        // Assert
        var state: LoginScreenState? = null
        vm.state.take(1).collect {
            state = it
        }
        assert(state is LoginScreenState.Login) {
            "Expected LoginScreenState.Login, but was $state"
        }
    }

    @Test
    fun testErrorOnLogin() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val service = FakeService()
        val vm = LoginViewModel(service)

        // Act
        vm.login("", "")
        service.unlock()

        // Assert
        var state: LoginScreenState? = null
        vm.state.take(1).collect {
            state = it
        }
        assert(state is LoginScreenState.Error) {
            "Expected LoginScreenState.Error, but was $state"
        }
    }

    @Test
    fun testStateIsLoading() = runTest(dispatcherRule.testDispatcher) {
        // Arrange
        val service = FakeService()
        val vm = LoginViewModel(service)

        // Act
        vm.login("", "")

        delay(50)
        // Assert
        var state: LoginScreenState? = null
        vm.state.take(1).collect {
            state = it
        }
        assert(state is LoginScreenState.Loading) {
            "Expected LoginScreenState.Loading, but was $state"
        }
        service.unlock()
    }
}