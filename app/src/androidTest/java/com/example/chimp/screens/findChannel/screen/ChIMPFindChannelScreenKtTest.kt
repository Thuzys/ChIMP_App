package com.example.chimp.screens.findChannel.screen

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.findChannel.service.FakeService
import com.example.chimp.screens.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.utils.repository.FakeChannelRepositoryRule
import com.example.chimp.utils.repository.FakeUserInfoRepositoryRule
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test


class ChIMPFindChannelScreenKtTest {
    @get:Rule
    val rule = createComposeRule()

    @get:Rule
    val fakeUserInfoRepo = FakeUserInfoRepositoryRule()

    @get:Rule
    val fakeChannelRepo = FakeChannelRepositoryRule()

    @Test
    fun error_state_displays_error_view() {
        val vm = FindChannelViewModel(
            service = FakeService(),
            userInfoRepository = fakeUserInfoRepo.repo,
            channelRepository = fakeChannelRepo.repo,
            initialState = FindChannelScreenState.Error(
                error = ResponseError("Error message"),
                goBack = FindChannelScreenState.Initial
            )
        )
        rule.setContent {
            ChIMPFindChannelScreen(
                modifier = Modifier,
                viewModel = vm,
                onChatsNavigate = {},
                onAboutNavigate = {},
                onRegisterNavigate = {},
                onJoinNavigate = {},
                onCreateChannelNavigate = {}
            )
        }

        rule
            .onNodeWithTag(testTag = FIND_CHANNEL_ERROR_VIEW_TAG)
            .assertExists()
    }

    @Test
    fun channel_info_state_displays_channel_info_view() {
        val vm = FindChannelViewModel(
            service = FakeService(),
            userInfoRepository = fakeUserInfoRepo.repo,
            channelRepository = fakeChannelRepo.repo,
            initialState = FindChannelScreenState.Info(
                channel = ChannelInfo(
                    cId = 1u,
                    name = ChannelName("Channel", "Channel"),
                    owner = UserInfo(1u, "Owner")
                ),
                goBack = FindChannelScreenState.Initial
            )
        )
        rule.setContent {
            ChIMPFindChannelScreen(
                modifier = Modifier,
                viewModel = vm,
                onChatsNavigate = {},
                onAboutNavigate = {},
                onRegisterNavigate = {},
                onJoinNavigate = {},
                onCreateChannelNavigate = {}
            )
        }

        rule
            .onNodeWithTag(testTag = FIND_CHANNEL_INFO_VIEW_TAG)
            .assertExists()
    }

    @Test
    fun loading_state_displays_loading_view() {
        val vm = FindChannelViewModel(
            service = FakeService(),
            userInfoRepository = fakeUserInfoRepo.repo,
            channelRepository = fakeChannelRepo.repo,
            initialState = FindChannelScreenState.Loading
        )
        rule.setContent {
            ChIMPFindChannelScreen(
                modifier = Modifier,
                viewModel = vm,
                onChatsNavigate = {},
                onAboutNavigate = {},
                onRegisterNavigate = {},
                onJoinNavigate = {},
                onCreateChannelNavigate = {}
            )
        }

        rule
            .onNodeWithTag(testTag = FIND_CHANNEL_LOADING_VIEW_TEST_TAG)
            .assertExists()
    }

    @Test
    fun normal_scrolling_state_displays_scrolling_view() {
        val vm = FindChannelViewModel(
            service = FakeService(),
            userInfoRepository = fakeUserInfoRepo.repo,
            channelRepository = fakeChannelRepo.repo,
            initialState = FindChannelScreenState.NormalScrolling(
                publicChannels = flowOf(emptyList()),
                hasMore = flowOf(false)
            )
        )
        rule.setContent {
            ChIMPFindChannelScreen(
                modifier = Modifier,
                viewModel = vm,
                onChatsNavigate = {},
                onAboutNavigate = {},
                onRegisterNavigate = {},
                onJoinNavigate = {},
                onCreateChannelNavigate = {}
            )
        }

        rule
            .onNodeWithTag(testTag = FIND_CHANNEL_SCROLLING_TAG)
            .assertExists()
    }

    @Test
    fun searching_scrolling_state_displays_scrolling_view() {
        val vm = FindChannelViewModel(
            service = FakeService(),
            userInfoRepository = fakeUserInfoRepo.repo,
            channelRepository = fakeChannelRepo.repo,
            initialState = FindChannelScreenState.SearchingScrolling(
                publicChannels = flowOf(emptyList()),
                hasMore = flowOf(false),
                searchChannelInput = "Channel"
            )
        )
        rule.setContent {
            ChIMPFindChannelScreen(
                modifier = Modifier,
                viewModel = vm,
                onChatsNavigate = {},
                onAboutNavigate = {},
                onRegisterNavigate = {},
                onJoinNavigate = {},
                onCreateChannelNavigate = {}
            )
        }

        rule
            .onNodeWithTag(testTag = FIND_CHANNEL_SCROLLING_TAG)
            .assertExists()
    }

    @Test
    fun back_to_registration_state_calls_register_navigate() {
        val vm = FindChannelViewModel(
            service = FakeService(),
            userInfoRepository = fakeUserInfoRepo.repo,
            channelRepository = fakeChannelRepo.repo,
            initialState = FindChannelScreenState.BackToRegistration
        )
        var called = false
        val onRegisterNavigate = { called = true }
        rule.setContent {
            ChIMPFindChannelScreen(
                modifier = Modifier,
                viewModel = vm,
                onChatsNavigate = {},
                onAboutNavigate = {},
                onRegisterNavigate = onRegisterNavigate,
                onJoinNavigate = {},
                onCreateChannelNavigate = {}
            )
        }

        assert(called)
    }
}