package com.example.chimp.screens.login.viewModel

import com.example.chimp.screens.login.service.FakeService
import com.example.chimp.screens.login.viewModel.state.Login
import com.example.chimp.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginViewModelTest {
    private val dispatcherRule = ReplaceMainDispatcherRule()

    @Test
    fun testViewModelInitialState() =
        runTest(dispatcherRule.testDispatcher) {
            // Arrange
            val vm = LoginViewModel(FakeService())

            // Act
            val state = vm.state.value

            // Assert
            assert(state is Login.LoginHide)
        }

//    @Test
//    fun testViewModelLogin() =
//        runTest(dispatcherRule.testDispatcher) {
//            // Arrange
//            val service = FakeService()
//            val vm = LoginViewModel(service)
//            vm.updateUsername(service.validUsername)
//            vm.updatePassword(service.validPassword)
//
//            // Act
//            vm.login()
//            service.unlock()
//            // Assert
//            val state = vm.state
//                .takeWhile { it !is LoginScreenState.Success }
//                .collectIndexed { index, value ->
//                    println("index: $index, value: $value")
//                }
//        }
}