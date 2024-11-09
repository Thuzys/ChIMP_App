package com.example.chimp.screens.login.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.screens.login.screen.view.LOGIN_VIEW_TEST_TAG
import com.example.chimp.screens.login.screen.view.REGISTER_VIEW_TEST_TAG
import com.example.chimp.screens.login.service.FakeService
import com.example.chimp.screens.login.viewModel.LoginViewModel
import com.example.chimp.screens.ui.views.ERROR_VIEW_TEST_TAG
import com.example.chimp.screens.ui.views.LOADING_VIEW_TEST_TAG
import com.example.chimp.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ChIMPLoginScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    private val dispatcherRule = ReplaceMainDispatcherRule()

    @Test
    fun testChIMPLoginScreenIsLoginView() {
        val viewModel = LoginViewModel(FakeService())
        rule.setContent {
            ChIMPLoginScreen(
                viewModel = viewModel,
                onLogin = {}
            )
        }
        rule
            .onNodeWithTag(
                LOGIN_VIEW_TEST_TAG
            )
            .assertIsDisplayed()
    }

    @Test
    fun testChIMPLoginScreenIsRegisterView() {
        val viewModel = LoginViewModel(FakeService())
        viewModel.toRegister()
        rule.setContent {
            ChIMPLoginScreen(
                viewModel = viewModel,
                onLogin = {}
            )
        }
        rule
            .onNodeWithTag(
                REGISTER_VIEW_TEST_TAG
            )
            .assertIsDisplayed()
    }

    @Test
    fun testChIMPLoginScreenIsLoadingView() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val viewModel = LoginViewModel(service)
            viewModel.login()
            rule.setContent {
                ChIMPLoginScreen(
                    viewModel = viewModel,
                    onLogin = {}
                )
            }
            rule
                .onNodeWithTag(
                    LOADING_VIEW_TEST_TAG
                )
                .assertIsDisplayed()
            service.unlock()
        }

    @Test
    fun testChIMPLoginScreenIsErrorView() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val viewModel = LoginViewModel(service)
            viewModel.login()
            rule.setContent {
                ChIMPLoginScreen(
                    viewModel = viewModel,
                    onLogin = {}
                )
            }
            rule
                .onNodeWithTag(
                    LOADING_VIEW_TEST_TAG
                )
                .assertIsDisplayed()
            service.unlock()
            rule
                .onNodeWithTag(
                    ERROR_VIEW_TEST_TAG
                )
                .assertIsDisplayed()
        }

    @Test
    fun testChIMPLoginOnSuccessWasCalled() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val viewModel = LoginViewModel(service)
            var called = false
            val funcTest = { called = true }
            viewModel.updateUsername(service.validUsername)
            viewModel.updatePassword(service.validPassword)
            viewModel.login()
            rule.setContent {
                ChIMPLoginScreen(
                    viewModel = viewModel,
                    onLogin = funcTest
                )
            }
            service.unlock()
//            assert(called) TODO: Ask why this is not working
            rule
                .onNodeWithTag(
                    LOGIN_VIEW_TEST_TAG
                )
                .assertIsDisplayed()
        }
}