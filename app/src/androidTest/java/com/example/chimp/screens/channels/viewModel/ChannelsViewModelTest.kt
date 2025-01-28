package com.example.chimp.screens.channels.viewModel

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.CONNECTED
import com.example.chimp.screens.channels.service.FakeService
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.BackToRegistration
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Error
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Initial
import com.example.chimp.screens.channels.viewModel.state.ChannelsScreenState.Scrolling
import com.example.chimp.utils.repository.FakeUserInfoRepositoryRule
import com.example.chimp.utils.ReplaceMainDispatcherRule
import com.example.chimp.utils.repository.FakeChannelRepositoryRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Rule
import org.junit.Test

class ChannelsViewModelTest {

    @get:Rule
    val dispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val fakeUserInfoRepo = FakeUserInfoRepositoryRule()

    @get:Rule
    val fakeChannelRepository = FakeChannelRepositoryRule()

    @Test
    fun view_model_initial_state_is_initial() =
        runTest(dispatcherRule.testDispatcher) {
            val vm = ChannelsViewModel(
                FakeService(),
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
            )

            assert(vm.state.value is Initial) {
                "Expected ChannelsScreenState.Initial, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_load_channels_goes_to_loading_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = ChannelsViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
            )

            vm.loadChannels()
            yield()

            assert(vm.state.value is ChannelsScreenState.Loading) {
                "Expected ChannelsScreenState.Loading, but was ${vm.state.value}"
            }
            service.unlock()
        }

    @Test
    fun view_model_loads_channels_goes_to_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = ChannelsViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
            )

            vm.loadChannels()
            service.unlock()

            assert(vm.state.value is Scrolling) {
                "Expected ChannelsScreenState.Scrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_toChannelInfo_returns_info_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                Scrolling(
                    flowOf(emptyList()),
                    flowOf(false),
                    flowOf(CONNECTED),
                )
            val channel =
                ChannelInfo(
                    cId = 1u,
                    name = ChannelName("test", "test"),
                    owner = UserInfo(1u, "test")
                )
            val vm = ChannelsViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
                initialState,
            )

            vm.toChannelInfo(channel)
            yield()

            assert(vm.state.value is ChannelsScreenState.Info) {
                "Expected ChannelsScreenState.Info, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_goes_back_to_previous_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                Scrolling(
                    flowOf(emptyList()),
                    flowOf(false),
                    flowOf(CONNECTED),
                )
            val vm = ChannelsViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
                Error(ResponseError("error", "error"), initialState),
            )

            vm.goBack()
            yield()

            assert(vm.state.value is Scrolling) {
                "Expected ChannelsScreenState.Scrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun loadMore_continues_in_state_scrolling() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                Scrolling(
                    flowOf(emptyList()),
                    flowOf(false),
                    flowOf(CONNECTED),
                )
            val vm = ChannelsViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
                initialState,
            )

            vm.loadMore()
            service.unlock()

            assert(vm.state.value is Scrolling) {
                "Expected ChannelsScreenState.Scrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun logout_logs_out_user() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                Scrolling(
                    flowOf(emptyList()),
                    flowOf(false),
                    flowOf(CONNECTED),
                )
            val vm = ChannelsViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepository.repo,
                initialState,
            )

            vm.logout()
            yield()

            assert(vm.state.value is BackToRegistration) {
                "Expected ChannelsScreenState.Initial, but was ${vm.state.value}"
            }
        }
}