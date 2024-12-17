package com.example.chimp.screens.register.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.register.screen.view.ERROR_VIEW_TEST_TAG
import com.example.chimp.screens.register.screen.view.LOGIN_VIEW_TEST_TAG
import com.example.chimp.screens.register.screen.view.REGISTER_VIEW_TEST_TAG
import com.example.chimp.screens.register.service.FakeService
import com.example.chimp.screens.register.viewModel.LoginViewModel
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Error
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Register
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Success
import com.example.chimp.screens.ui.views.LOADING_VIEW_TEST_TAG
import com.example.chimp.services.validation.ChIMPFormValidator
import org.junit.Rule
import org.junit.Test

class ChIMPLogInScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun chimp_register_screen_is_in_login_view() {
        val viewModel = LoginViewModel(FakeService(), ChIMPFormValidator())
        rule.setContent {
            ChIMPLoginScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(LOGIN_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun chimp_register_screen_is_in_register_view() {
        val viewModel = LoginViewModel(FakeService(), ChIMPFormValidator(), Register())
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
    fun chimp_register_screen_is_in_loading_view() {
        val viewModel = LoginViewModel(FakeService(), ChIMPFormValidator(), RegisterScreenState.Loading)
        rule.setContent {
            ChIMPLoginScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(LOADING_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun chimp_register_screen_is_in_error_view() {
        val error = Error(
            username = "some user",
            error = ResponseErrors("some cause", "some url")
        )
        val viewModel = LoginViewModel(FakeService(), ChIMPFormValidator(), error)
        rule.setContent { ChIMPLoginScreen(viewModel = viewModel) }
        rule
            .onNodeWithTag(ERROR_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun on_login_was_called_in_success_view() {
        var called = false
        val user = User(1u, "some user", "some token")
        val viewModel = LoginViewModel(FakeService(), ChIMPFormValidator(), Success(user))
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