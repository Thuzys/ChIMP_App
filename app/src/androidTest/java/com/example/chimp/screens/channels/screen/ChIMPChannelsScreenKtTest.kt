package com.example.chimp.screens.channels.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.screens.channels.screen.view.CHANNEL_INFO_VIEW_TAG
import com.example.chimp.screens.channels.screen.view.CHANNEL_SCROLLING_VIEW
import com.example.chimp.screens.channels.service.FakeService
import com.example.chimp.screens.channels.viewModel.ChannelsViewModel
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.BackToRegistration
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Error
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Info
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Loading
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Scrolling
import com.example.chimp.screens.ui.views.ERROR_VIEW_TEST_TAG
import com.example.chimp.screens.ui.views.LOADING_VIEW_TEST_TAG
import com.example.chimp.utils.repository.FakeUserInfoRepositoryRule
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
//
//class ChIMPChannelsScreenKtTest {
//
//        @get:Rule
//        val rule = createComposeRule()
//
//        @get:Rule
//        val repo = FakeUserInfoRepositoryRule()
//
//        @Test
//        fun scrolling_state_view_in_Channel_screen() {
//            val service = FakeService()
//            val vm = ChannelsViewModel(
//                service = service,
//                userInfoRepository = repo.repo,
//                initialState = Scrolling(
//                    channels = flowOf(emptyList()),
//                    hasMore = flowOf(false),
//                ),
//            )
//
//            rule.setContent { ChIMPChannelsScreen(vm = vm) }
//
//            rule
//                .onNodeWithTag(
//                    testTag = CHANNEL_SCROLLING_VIEW,
//                    useUnmergedTree = true
//                )
//                .assertIsDisplayed()
//        }
//
//    @Test
//    fun loading_state_view_in_Channel_screen() {
//        val service = FakeService()
//        val vm = ChannelsViewModel(
//            service = service,
//            userInfoRepository = repo.repo,
//            initialState = Loading
//        )
//
//        rule.setContent { ChIMPChannelsScreen(vm = vm) }
//
//        rule
//            .onNodeWithTag(LOADING_VIEW_TEST_TAG)
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun error_state_view_in_Channel_screen() {
//        val service = FakeService()
//        val vm = ChannelsViewModel(
//            service = service,
//            userInfoRepository = repo.repo,
//            initialState = Error(ResponseError("Error", "Error"), Loading)
//        )
//
//        rule.setContent { ChIMPChannelsScreen(vm = vm) }
//
//        rule
//            .onNodeWithTag(ERROR_VIEW_TEST_TAG)
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun info_state_view_in_Channel_screen() {
//        val service = FakeService()
//        val channel = ChannelInfo(
//            cId = 1u,
//            name = ChannelName("name", "name"),
//            owner = UserInfo(1u, "test")
//        )
//        val vm = ChannelsViewModel(
//            service = service,
//            userInfoRepository = repo.repo,
//            initialState = Info(channel, Loading)
//        )
//
//        rule.setContent { ChIMPChannelsScreen(vm = vm) }
//
//        rule
//            .onNodeWithTag(CHANNEL_INFO_VIEW_TAG)
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun back_to_register_screen() {
//        val service = FakeService()
//        var success = false
//        val funcTest = { success = true }
//        val vm = ChannelsViewModel(
//            service = service,
//            userInfoRepository = repo.repo,
//            initialState = BackToRegistration
//        )
//        rule.setContent {
//            ChIMPChannelsScreen(vm = vm, onRegisterNavigate = funcTest)
//        }
//
//        assert(success)
//    }
//}