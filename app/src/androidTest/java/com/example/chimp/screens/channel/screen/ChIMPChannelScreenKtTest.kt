package com.example.chimp.screens.channel.screen

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.DISCONNECTED
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import com.example.chimp.screens.channel.screen.view.CREATE_INVITATION_VIEW_TAG
import com.example.chimp.screens.channel.screen.view.EDIT_CHANNEL_VIEW_TAG
import com.example.chimp.screens.channel.screen.view.SCROLLING_VIEW
import com.example.chimp.screens.channel.service.FakeService
import com.example.chimp.screens.channel.viewModel.ChannelViewModel
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.CreatingInvitation
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Editing
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Error
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Loading
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.ShowingInvitation
import com.example.chimp.screens.ui.views.CHANNEL_INFO_VIEW_TAG
import com.example.chimp.screens.ui.views.ERROR_VIEW_TEST_TAG
import com.example.chimp.screens.ui.views.LOADING_VIEW_TEST_TAG
import com.example.chimp.screens.ui.views.SHOWING_INVITATION_VIEW_TAG
import com.example.chimp.utils.repository.FakeChannelRepositoryRule
import com.example.chimp.utils.repository.FakeUserInfoRepositoryRule
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ChIMPChannelScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    @get:Rule
    val fakeUserInfoRepo = FakeUserInfoRepositoryRule()

    @get:Rule
    val fakeChannelRepo = FakeChannelRepositoryRule()

    @Test
    fun loading_state_displays_loading_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = Loading
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = LOADING_VIEW_TEST_TAG)
            .assertExists()
    }

    @Test
    fun scrolling_state_display_scrolling_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = Scrolling(
                channel = ChannelInfo(
                    cId = 1u,
                    name = ChannelName("Channel Name", "Channel Name"),
                    owner = UserInfo(1u, "Owner Name"),
                ),
                user = UserInfo(1u, "Name"),
                messages = flowOf(emptyList()),
                hasMore = flowOf(false),
                connection = flowOf(DISCONNECTED),
                accessControl = READ_WRITE
            )
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = SCROLLING_VIEW)
            .assertExists()
    }

    @Test
    fun editing_state_display_editing_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = Editing(
                channel = ChannelInfo(
                    cId = 1u,
                    name = ChannelName("Channel Name", "Channel Name"),
                    owner = UserInfo(1u, "Owner Name"),
                ),
                previous = Loading
            )
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = EDIT_CHANNEL_VIEW_TAG)
            .assertExists()
    }

    @Test
    fun error_state_display_error_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = Error(
                error = ResponseError.InternalServerError,
                previous = Loading
            )
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = ERROR_VIEW_TEST_TAG)
            .assertExists()
    }

    @Test
    fun info_state_display_info_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = ChannelScreenState.Info(
                channel = ChannelInfo(
                    cId = 1u,
                    name = ChannelName("Channel Name", "Channel Name"),
                    owner = UserInfo(1u, "Owner Name"),
                ),
                previous = Loading
            )
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = CHANNEL_INFO_VIEW_TAG)
            .assertExists()
    }

    @Test
    fun crateInvitation_state_display_invitation_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = CreatingInvitation(
                previous = Loading
            )
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = CREATE_INVITATION_VIEW_TAG)
            .assertExists()
    }

    @Test
    fun showInvitation_state_display_showing_invitation_view() {
        val vm = ChannelViewModel(
            channelRepo = fakeChannelRepo.repo,
            userRepo = fakeUserInfoRepo.repo,
            service = FakeService(),
            initialState = ShowingInvitation(
                invitation = "Invitation",
                previous = Loading
            )
        )
        rule.setContent {
            ChIMPChannelScreen(
                modifier = Modifier,
                vm = vm,
                onBack = {}
            )
        }

        rule
            .onNodeWithTag(testTag = SHOWING_INVITATION_VIEW_TAG)
            .assertExists()
    }
}