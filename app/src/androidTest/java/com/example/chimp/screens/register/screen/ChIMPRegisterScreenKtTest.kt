package com.example.chimp.screens.register.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.screens.register.screen.view.LOGIN_VIEW_TEST_TAG
import com.example.chimp.screens.register.screen.view.REGISTER_VIEW_TEST_TAG
import com.example.chimp.screens.register.service.FakeService
import com.example.chimp.screens.register.viewModel.RegisterViewModel
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Error
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Register
import com.example.chimp.screens.register.viewModel.state.RegisterScreenState.Success
import com.example.chimp.screens.ui.views.ERROR_VIEW_TEST_TAG
import com.example.chimp.screens.ui.views.LOADING_VIEW_TEST_TAG
import com.example.chimp.services.validation.ChIMPFormValidator
import com.example.chimp.utils.FakeUserInfoRepositoryRule
import org.junit.Rule
import org.junit.Test

class ChIMPRegisterScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    @get:Rule
    val fakeRepo = FakeUserInfoRepositoryRule()

    @Test
    fun chimp_register_screen_is_in_login_view() {
        val viewModel = RegisterViewModel(
            FakeService(),
            ChIMPFormValidator(),
            fakeRepo.repo
        )
        rule.setContent {
            ChIMPLoginScreen(viewModel = viewModel)
        }
        rule
            .onNodeWithTag(LOGIN_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun chimp_register_screen_is_in_register_view() {
        val viewModel = RegisterViewModel(
            FakeService(),
            ChIMPFormValidator(),
            fakeRepo.repo,
            Register(),
        )
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
        val viewModel = RegisterViewModel(
            FakeService(),
            ChIMPFormValidator(),
            fakeRepo.repo,
            RegisterScreenState.Loading
        )
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
            error = ResponseError("some cause", "some url")
        )
        val viewModel = RegisterViewModel(
            FakeService(),
            ChIMPFormValidator(),
            fakeRepo.repo,
            error
        )
        rule.setContent { ChIMPLoginScreen(viewModel = viewModel) }
        rule
            .onNodeWithTag(ERROR_VIEW_TEST_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun on_login_was_called_in_success_view() {
        var called = false
        val viewModel = RegisterViewModel(
            FakeService(),
            ChIMPFormValidator(),
            fakeRepo.repo,
            Success
        )
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