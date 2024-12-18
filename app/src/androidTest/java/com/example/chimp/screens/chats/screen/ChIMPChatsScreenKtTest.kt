package com.example.chimp.screens.chats.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.errors.ResponseErrors
import com.example.chimp.models.users.User
import com.example.chimp.screens.chats.screen.view.CHATS_IDLE_VIEW_TAG
import com.example.chimp.screens.chats.service.FakeService
import com.example.chimp.screens.chats.viewModel.ChatsViewModel
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState
import com.example.chimp.screens.chats.viewModel.state.ChatsScreenState.Idle
import org.junit.Rule
import org.junit.Test

class ChIMPChatsScreenKtTest {

        @get:Rule
        val rule = createComposeRule()

        @Test
        fun idle_state_view_in_ChIMP_screen() {
            val service = FakeService()
            val vm = ChatsViewModel(
                service = service,
                user = User(1u, "User", "Test"),
                initialState = Idle(emptyList())
            )
            rule.setContent {
                ChIMPChatsScreen(
                    vm = vm
                )
            }
            rule
                .onNodeWithTag(
                    testTag = CHATS_IDLE_VIEW_TAG,
                    useUnmergedTree = true
                )
                .assertIsDisplayed()
        }

    @Test
    fun loading_state_view_in_ChIMP_screen() {
        val service = FakeService()
        val vm = ChatsViewModel(
            service = service,
            user = User(1u, "User", "Test"),
            initialState = ChatsScreenState.Loading
        )
        rule.setContent {
            ChIMPChatsScreen(
                vm = vm
            )
        }
        rule
            .onNodeWithTag("ChatsLoadingView")
            .assertIsDisplayed()
    }

    @Test
    fun error_state_view_in_ChIMP_screen() {
        val service = FakeService()
        val vm = ChatsViewModel(
            service = service,
            user = User(1u, "User", "Test"),
            initialState = ChatsScreenState.Error(ResponseErrors("Error", "Error"))
        )
        rule.setContent {
            ChIMPChatsScreen(
                vm = vm
            )
        }
        rule
            .onNodeWithTag("ChatsErrorView")
            .assertIsDisplayed()
    }

    @Test
    fun chat_selected_state_view_in_ChIMP_screen() {
        val service = FakeService()
        val vm = ChatsViewModel(
            service = service,
            user = User(1u, "User", "Test"),
        )
        rule.setContent {
            ChIMPChatsScreen(
                vm = vm
            )
        }
        rule
            .onNodeWithTag("ChatsErrorView")
            .assertIsDisplayed()
    }
}