package com.example.chimp.screens.channel.viewModel

import com.example.chimp.models.channel.ChannelInfo
import com.example.chimp.models.channel.ChannelName
import com.example.chimp.models.errors.ResponseError
import com.example.chimp.models.users.Token
import com.example.chimp.models.users.User
import com.example.chimp.models.users.UserInfo
import com.example.chimp.observeConnectivity.ConnectivityObserver.Status.CONNECTED
import com.example.chimp.screens.channel.model.accessControl.AccessControl.READ_WRITE
import com.example.chimp.screens.channel.service.FakeService
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Editing
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Initial
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Loading
import com.example.chimp.screens.channel.viewModel.state.ChannelScreenState.Scrolling
import com.example.chimp.utils.repository.FakeUserInfoRepositoryRule
import com.example.chimp.utils.ReplaceMainDispatcherRule
import com.example.chimp.utils.repository.FakeChannelRepositoryRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Rule
import org.junit.Test

class ChannelViewModelTest {

    @get:Rule
    val dispatcherRule = ReplaceMainDispatcherRule()

    @get:Rule
    val fakeUserInfoRepo = FakeUserInfoRepositoryRule()

    @get:Rule
    val fakeChannelRepo = FakeChannelRepositoryRule()

    @Test
    fun view_model_initial_state_is_initial() =
        runTest(dispatcherRule.testDispatcher) {
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                FakeService(),
                fakeUserInfoRepo.repo,
            )

            assert(vm.state.value is Initial) {
                "Expected ChannelScreenState.Initial, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_load_messages_goes_to_loading_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
            )

            fakeUserInfoRepo.repo.updateUserInfo(User(1u, "test", Token("test")))
            fakeChannelRepo.repo.updateChannelInfo(
                ChannelInfo(
                    1u,
                    ChannelName("test", "test"),
                    owner = UserInfo(1u, "test")
                )
            )

            vm.loadMessages()
            yield()

            assert(vm.state.value is Loading) {
                "Expected ChannelScreenState.Loading, but was ${vm.state.value}"
            }
            service.unlock()
        }

    @Test
    fun view_model_load_messages_goes_to_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
            )

            fakeUserInfoRepo.repo.updateUserInfo(User(1u, "test", Token("test")))
            fakeChannelRepo.repo.updateChannelInfo(
                ChannelInfo(
                    1u,
                    ChannelName("test", "test"),
                    owner = UserInfo(1u, "test")
                )
            )

            vm.loadMessages()
            service.unlock()
            service.unlock()
            yield()
            assert(vm.state.value is Scrolling) {
                "Expected ChannelScreenState.Scrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_toEdit_goes_to_editing_state() =
        runTest(dispatcherRule.testDispatcher) {
            val userInfo = UserInfo(1u, "test")
            val channelInfo = ChannelInfo(
                1u,
                ChannelName("test", "test"),
                owner = userInfo,
            )
            val service = FakeService()
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
                Scrolling(
                    channel = channelInfo,
                    user = userInfo,
                    accessControl = READ_WRITE,
                    messages = flowOf(emptyList()),
                    hasMore = flowOf(false),
                    connection = flowOf(CONNECTED)
                )
            )

            fakeChannelRepo.repo.updateChannelInfo(channelInfo)

            vm.toEdit()
            yield()

            assert(vm.state.value is Editing) {
                "Expected ChannelScreenState.Editing, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_goBack_goes_to_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
                ChannelScreenState.Error(ResponseError("error", "error"), Loading)
            )

            vm.goBack()
            yield()

            assert(vm.state.value is Loading) {
                "Expected ChannelScreenState.Loading, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_loadMore_continues_in_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val user = UserInfo(1u, "test")
            val channel = ChannelInfo(
                1u,
                ChannelName("test", "test"),
                owner = user,
            )
            val service = FakeService()
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
                Scrolling(
                    channel = channel,
                    user = user,
                    accessControl = READ_WRITE,
                    messages = flowOf(emptyList()),
                    hasMore = flowOf(true),
                    connection = flowOf(CONNECTED)
                )
            )

            vm.loadMore()
            service.unlock()

            assert(vm.state.value is Scrolling) {
                "Expected ChannelScreenState.Scrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun view_model_editChannel_goes_to_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val user = UserInfo(1u, "test")
            val channel = ChannelInfo(
                1u,
                ChannelName("test", "test"),
                owner = user,
            )
            val service = FakeService()
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
                Editing(
                    channel,
                    Scrolling(
                        channel = channel,
                        user = user,
                        accessControl = READ_WRITE,
                        messages = flowOf(emptyList()),
                        hasMore = flowOf(false),
                        connection = flowOf(CONNECTED)
                    )
                )
            )

            vm.editChannel(channel)
            service.unlock()

            assert(vm.state.value is Scrolling) {
                "Expected ChannelScreenState.Scrolling, but was ${vm.state.value}"
            }
        }

    @Test
    fun send_message_continues_in_scrolling_state() =
        runTest(dispatcherRule.testDispatcher) {
            val service = FakeService()
            val user = User(1u, "test", Token("test"))
            val channel = ChannelInfo(
                1u,
                ChannelName("test", "test"),
                owner = UserInfo(1u, "test"),
            )
            val vm = ChannelViewModel(
                fakeChannelRepo.repo,
                service,
                fakeUserInfoRepo.repo,
                Scrolling(
                    channel = channel,
                    user = UserInfo(1u, "test"),
                    accessControl = READ_WRITE,
                    messages = flowOf(emptyList()),
                    hasMore = flowOf(false),
                    connection = flowOf(CONNECTED)
                )
            )

            fakeChannelRepo
                .repo
                .updateChannelInfo(
                    FakeChannelRepositoryRule.channelWithMessages
                )

            fakeUserInfoRepo
                .repo
                .updateUserInfo(
                    user
                )

            vm.sendMessage("test")
            service.unlock()

            assert(vm.state.value is Scrolling) {
                "Expected ChannelScreenState.Scrolling, but was ${vm.state.value}"
            }
        }
}