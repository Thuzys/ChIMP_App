package com.example.chimp.screens.findChannel.viewModel

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.UserInfo
import com.example.chimp.screens.findChannel.service.FakeService
import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
import com.example.chimp.utils.ReplaceMainDispatcherRule
import com.example.chimp.utils.repository.FakeChannelRepositoryRule
import com.example.chimp.utils.repository.FakeUserInfoRepositoryRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Rule
import org.junit.Test

class FindChannelViewModelTest {
    @get:Rule
    val dispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val fakeUserInfoRepo = FakeUserInfoRepositoryRule()

    @get:Rule
    val fakeChannelRepo = FakeChannelRepositoryRule()

    @Test
    fun view_model_initial_state_is_initial() =
        runTest(dispatcherRule.testDispatcher) {
            val vm = FindChannelViewModel(
                FakeService(),
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
            )

            assert(vm.state.value is FindChannelScreenState.Initial) {
                "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_get_channels_goes_to_loading_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
            )

            vm.getChannels()
            yield()

            assert(vm.state.value is FindChannelScreenState.Loading) {
                "Expected FindChannelScreenState.Loading, but was ${vm.state.value}"
            }
            service.unlock()
        }

    @Test
    fun view_model_get_channels_goes_to_normal_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
            )

            vm.getChannels()
            service.unlock()

            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_toChannelInfo_returns_Info() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.toChannelInfo(
                ChannelInfo(
                    1u,
                    ChannelName("test", "test"),
                    owner = UserInfo(1u, "test")
                )
            )
            yield()

            assert(vm.state.value is FindChannelScreenState.Info) {
                "Expected FindChannelScreenState.Loading, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_goes_back_to_previous_state_when_error() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                FindChannelScreenState.Error(
                    ResponseError("error", "error"),FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
                )

            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.goBack()
            yield()

            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_goes_back_to_previous_state_when_info() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                FindChannelScreenState.Info(
                    ChannelInfo(1u, ChannelName("test", "test"), owner = UserInfo(1u, "test")),
                    FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
                )

            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.goBack()
            yield()

            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_does_not_go_back_when_state_is_not_info_or_error() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState = FindChannelScreenState.Initial
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.goBack()
            yield()

            assert(vm.state.value is FindChannelScreenState.Initial) {
                "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
            }
        }

    @Test
    fun load_more_continues_in_state_normal_scrolling() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.loadMore()
            service.unlock()

            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun logout_goes_to_registration() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.logout()
            yield()

            assert(vm.state.value is FindChannelScreenState.BackToRegistration) {
                "Expected FindChannelScreenState.BackToRegistration, but was ${vm.state.value}"
            }
        }

    @Test
    fun reset_goes_to_initial() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.reset()
            yield()

            assert(vm.state.value is FindChannelScreenState.Initial) {
                "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
            }
        }

    @Test
    fun get_channels_goes_to_searching_scrolling() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
            )

            vm.getChannels("test")
            service.unlock()

            assert(vm.state.value is FindChannelScreenState.SearchingScrolling) {
                "Expected FindChannelScreenState.SearchingScrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun load_more_continues_in_state_searching_scrolling() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val initialState =
                FindChannelScreenState.SearchingScrolling("test", flowOf(emptyList()), flowOf(true))
            val vm = FindChannelViewModel(
                service,
                fakeUserInfoRepo.repo,
                fakeChannelRepo.repo,
                initialState,
            )

            vm.loadMore("test")
            service.unlock()

            assert(vm.state.value is FindChannelScreenState.SearchingScrolling) {
                "Expected FindChannelScreenState.SearchingScrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun joinChannel_goes_to_loading_state() = runTest(dispatcherRule.testDispatcher) {
        val service = FakeService()
        val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
        val vm = FindChannelViewModel(
            service,
            fakeUserInfoRepo.repo,
            fakeChannelRepo.repo,
            initialState,
        )

        vm.joinChannel(1u) {}
        yield()

        assert(vm.state.value is FindChannelScreenState.Loading) {
            "Expected FindChannelScreenState.Loading, but was ${vm.state.value}"
        }
    }

    @Test
    fun joinChannel_navigates_to_channel_on_success() = runTest(dispatcherRule.testDispatcher) {
        val service = FakeService()
        val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
        val vm = FindChannelViewModel(
            service,
            fakeUserInfoRepo.repo,
            fakeChannelRepo.repo,
            initialState,
        )

        var navigated = false
        vm.joinChannel(1u) { navigated = true }
        service.unlock()
        yield()

        assert(navigated) { "Expected navigation to channel, but it did not happen" }
    }

    @Test
    fun joinChannel_goes_to_Error_state_on_failure() = runTest(dispatcherRule.testDispatcher) {
        val service = FakeService()
        val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
        val vm = FindChannelViewModel(
            service,
            fakeUserInfoRepo.repo,
            fakeChannelRepo.repo,
            initialState,
        )

        vm.joinChannel(2u) {}
        service.unlock()
        yield()

        assert(vm.state.value is FindChannelScreenState.Error) {
            "Expected FindChannelScreenState.Error, but was ${vm.state.value}"
        }
    }

    @Test
    fun joinChannel_goes_to_registration_on_unauthorized() = runTest(dispatcherRule.testDispatcher) {
        val service = FakeService()
        val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
        val vm = FindChannelViewModel(
            service,
            fakeUserInfoRepo.repo,
            fakeChannelRepo.repo,
            initialState,
        )

        vm.joinChannel(3u) {}
        service.unlock()
        yield()

        assert(vm.state.value is FindChannelScreenState.BackToRegistration) {
            "Expected FindChannelScreenState.BackToRegistration, but was ${vm.state.value}"
        }
    }

    @Test
    fun searchText_debounce_triggers_search() = runTest(dispatcherRule.testDispatcher) {
        val service = FakeService()
        val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
        val vm = FindChannelViewModel(
            service,
            fakeUserInfoRepo.repo,
            fakeChannelRepo.repo,
            initialState,
        )

        vm.updateSearchText("test")
        service.unlock()
        yield()

        assert(vm.state.value is FindChannelScreenState.SearchingScrolling) {
            "Expected FindChannelScreenState.SearchingScrolling, but was ${vm.state.value}"
        }
    }

    @Test
    fun searchText_empty_does_not_trigger_search() = runTest(dispatcherRule.testDispatcher) {
        val service = FakeService()
        val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
        val vm = FindChannelViewModel(
            service,
            fakeUserInfoRepo.repo,
            fakeChannelRepo.repo,
            initialState,
        )

        vm.updateSearchText("")
        yield()

        assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
            "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
        }
    }
}