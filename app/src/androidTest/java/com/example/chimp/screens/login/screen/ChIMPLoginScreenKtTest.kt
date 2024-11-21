package com.example.chimp.screens.login.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.login.screen.view.ERROR_VIEW_TEST_TAG
import com.example.chimp.screens.login.screen.view.LOGIN_VIEW_TEST_TAG
import com.example.chimp.screens.login.screen.view.REGISTER_VIEW_TEST_TAG
import com.example.chimp.screens.login.service.FakeService
import com.example.chimp.screens.login.viewModel.LoginViewModel
import com.example.chimp.screens.login.viewModel.state.LoginScreenState
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Error
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Register
import com.example.chimp.screens.login.viewModel.state.LoginScreenState.Success
import com.example.chimp.screens.ui.views.LOADING_VIEW_TEST_TAG
import org.junit.Rule
import org.junit.Test

class ChIMPLoginScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testChIMPLoginScreenIsLoginView() {
        val viewModel = LoginViewModel(FakeService())
        rule.setContent {
            ChIMPLoginScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(LOGIN_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun testChIMPLoginScreenIsRegisterView() {
        val viewModel = LoginViewModel(FakeService(), Register())
        rule.setContent {
            ChIMPLoginScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(
                REGISTER_VIEW_TEST_TAG
            )
            .assertIsDisplayed()
    }

    @Test
    fun testChIMPLoginScreenIsLoadingView() {
        val viewModel = LoginViewModel(FakeService(), LoginScreenState.Loading)
        rule.setContent {
            ChIMPLoginScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(LOADING_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun testChIMPLoginScreenIsErrorView() {
        val error = Error(
            username = "some user",
            error = ResponseErrors("some cause", "some url")
        )
        val viewModel = LoginViewModel(FakeService(), error)
        rule.setContent { ChIMPLoginScreen(viewModel = viewModel) }
        rule
            .onNodeWithTag(ERROR_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun testChIMPLoginOnSuccessWasCalled() {
        var called = false
        val user = User(1u, "some user", "some token")
        val viewModel = LoginViewModel(FakeService(), Success(user))
        val onLogin = { called = true }
        rule.setContent {
            ChIMPLoginScreen(
                viewModel = viewModel,
                onLogin = onLogin
            )
        }
        assert(called)
    }
}