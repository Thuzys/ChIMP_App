package com.example.chimp.screens.findChannel.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.chimp.application.DependenciesContainer
import com.example.chimp.observeConnectivity.ConnectivityObserver
import com.example.chimp.observeConnectivity.NetworkConnectivityObserver
import com.example.chimp.screens.channels.activity.ChannelsActivity
import com.example.chimp.screens.findChannel.screen.ChIMPFindChannelScreen
import com.example.chimp.screens.findChannel.viewModel.FindChannelVMFactory
import com.example.chimp.screens.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.screens.about.activity.AboutActivity
import com.example.chimp.screens.channel.activity.ChannelActivity
import com.example.chimp.screens.register.activity.RegisterActivity
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.ui.composable.SearchBar
import com.example.chimp.screens.ui.theme.ChIMPTheme
import com.example.chimp.services.http.dtos.input.channel.ChannelInputModel
import com.example.chimp.services.http.dtos.input.channel.ChannelNameInputModel
import com.example.chimp.services.http.dtos.input.channel.OwnerInputModel

/**
 * The activity that represents the find channel screen.
 */
class FindChannelActivity: ComponentActivity() {
    private val viewModel by viewModels<FindChannelViewModel>(
        factoryProducer = {
            FindChannelVMFactory(
                (application as DependenciesContainer).findChannelService,
                (application as DependenciesContainer).userInfoRepository,
                (application as DependenciesContainer).channelRepository
            )
        }
    )

    private val navigateToRegisterIntent: Intent by lazy {
        Intent(this, RegisterActivity::class.java)
    }

    private val navigateToAboutIntent by lazy {
        Intent(this, AboutActivity::class.java)
    }

    private val navigateToChatsIntent by lazy {
        Intent(this, ChannelsActivity::class.java)
    }

    private val navigateToChannelIntent by lazy {
        Intent(this, ChannelActivity::class.java)
    }

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                val status by connectivityObserver.observe().collectAsState(
                    initial = ConnectivityObserver.Status.DISCONNECTED
                )
                ChIMPFindChannelScreen(
                    viewModel = viewModel,
                    onAboutNavigate = { startActivity(navigateToAboutIntent) },
                    onChatsNavigate = { startActivity(navigateToChatsIntent) },
                    onRegisterNavigate = {
                        startActivity(navigateToRegisterIntent)
                        finish()
                    },
                    onJoinNavigate = {startActivity(navigateToChannelIntent)},
                    status = status
                )
            }
        }
    }
}