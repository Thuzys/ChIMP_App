//package com.example.chimp.screens.findChannel.viewModel
//
//import com.example.chimp.models.channel.ChannelBasicInfo
//import com.example.chimp.models.channel.ChannelInfo
//import com.example.chimp.models.channel.ChannelName
//import com.example.chimp.models.errors.ResponseError
//import com.example.chimp.models.users.UserInfo
//import com.example.chimp.screens.findChannel.service.FakeService
//import com.example.chimp.screens.findChannel.viewModel.state.FindChannelScreenState
//import com.example.chimp.utils.FakeUserInfoRepositoryRule
//import com.example.chimp.utils.ReplaceMainDispatcherRule
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.yield
//import org.junit.Rule
//import org.junit.Test
//
//class FindChannelViewModelTest {
//    @get:Rule
//    val dispatcherRule = ReplaceMainDispatcherRule()
//
//    @get:Rule
//    val fakeUserInfoRepo = FakeUserInfoRepositoryRule()
//
//    @Test
//    fun view_model_initial_state_is_initial() =
//        runTest(dispatcherRule.testDispatcher) {
//            val vm = FindChannelViewModel(
//                FakeService(),
//                fakeUserInfoRepo.repo,
//            )
//
//            assert(vm.state.value is FindChannelScreenState.Initial) {
//                "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun view_model_get_channels_goes_to_loading_state() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//            )
//
//            vm.getChannels()
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.Loading) {
//                "Expected FindChannelScreenState.Loading, but was ${vm.state.value}"
//            }
//            service.unlock()
//        }
//
//    @Test
//    fun view_model_get_channels_goes_to_normal_scrolling_state() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//            )
//
//            vm.getChannels()
//            service.unlock()
//
//            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
//                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun view_model_toChannelInfo_returns_loading() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.toChannelInfo(ChannelBasicInfo(1u, ChannelName("test"), UserInfo(1u, "test")))
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.Loading) {
//                "Expected FindChannelScreenState.Loading, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun view_model_toChannelInfo_returns_info_after_loading() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.toChannelInfo(ChannelBasicInfo(1u, ChannelName("test"), UserInfo(1u, "test")))
//            service.unlock()
//
//            assert(vm.state.value is FindChannelScreenState.Info) {
//                "Expected FindChannelScreenState.Info, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun view_model_goes_back_to_previous_state_when_error() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState =
//                FindChannelScreenState.Error(
//                    ResponseError("error", "error"),FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//                )
//
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.goBack()
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
//                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun view_model_goes_back_to_previous_state_when_info() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState =
//                FindChannelScreenState.Info(
//                    ChannelInfo(1u, ChannelName("test"), owner = UserInfo(1u, "test")),
//                    FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//                )
//
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.goBack()
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
//                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun view_model_does_not_go_back_when_state_is_not_info_or_error() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState = FindChannelScreenState.Initial
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.goBack()
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.Initial) {
//                "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun load_more_continues_in_state_normal_scrolling() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.loadMore()
//            service.unlock()
//
//            assert(vm.state.value is FindChannelScreenState.NormalScrolling) {
//                "Expected FindChannelScreenState.NormalScrolling, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun logout_goes_to_registration() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.logout()
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.BackToRegistration) {
//                "Expected FindChannelScreenState.BackToRegistration, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun reset_goes_to_initial() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState = FindChannelScreenState.NormalScrolling(flowOf(emptyList()), flowOf(true), "")
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.reset()
//            yield()
//
//            assert(vm.state.value is FindChannelScreenState.Initial) {
//                "Expected FindChannelScreenState.Initial, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun get_channels_goes_to_searching_scrolling() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//            )
//
//            vm.getChannels("test")
//            service.unlock()
//
//            assert(vm.state.value is FindChannelScreenState.SearchingScrolling) {
//                "Expected FindChannelScreenState.SearchingScrolling, but was ${vm.state.value}"
//            }
//        }
//
//    @Test
//    fun load_more_continues_in_state_searching_scrolling() =
//        runTest(dispatcherRule.testDispatcher) {
//            val service = FakeService()
//            val initialState =
//                FindChannelScreenState.SearchingScrolling("test", flowOf(emptyList()), flowOf(true))
//            val vm = FindChannelViewModel(
//                service,
//                fakeUserInfoRepo.repo,
//                initialState,
//            )
//
//            vm.loadMore("test")
//            service.unlock()
//
//            assert(vm.state.value is FindChannelScreenState.SearchingScrolling) {
//                "Expected FindChannelScreenState.SearchingScrolling, but was ${vm.state.value}"
//            }
//        }
//}