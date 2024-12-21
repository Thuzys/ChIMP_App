package com.example.chimp.screens.createChannel.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chimp.application.DependenciesContainer
import com.example.chimp.screens.about.activity.AboutActivity
import com.example.chimp.screens.channels.activity.ChannelsActivity
import com.example.chimp.screens.createChannel.screen.ChIMPCreateChannelScreen
import com.example.chimp.screens.createChannel.viewModel.CreateChannelVMFactory
import com.example.chimp.screens.createChannel.viewModel.CreateChannelViewModel
import com.example.chimp.screens.findChannel.activity.FindChannelActivity
import com.example.chimp.screens.register.activity.RegisterActivity
import com.example.chimp.screens.ui.theme.ChIMPTheme

class CreateChannelActivity: ComponentActivity() {
    private val viewModel by viewModels<CreateChannelViewModel>(
        factoryProducer = {
            CreateChannelVMFactory(
                (application as DependenciesContainer).createChannelService,
                (application as DependenciesContainer).userInfoRepository
            )
        }
    )

    private val navigateToChatsIntent by lazy {
        Intent(this, ChannelsActivity::class.java)
    }

    private val navigateToFindChannelIntent by lazy {
        Intent(this, FindChannelActivity::class.java)
    }

    private val navigateToAboutIntent by lazy {
        Intent(this, AboutActivity::class.java)
    }

    private val navigateToRegisterIntent by lazy {
        Intent(this, RegisterActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChIMPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ChIMPCreateChannelScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        onAboutNavigate = { startActivity(navigateToAboutIntent) },
                        onChatsNavigate = { startActivity(navigateToChatsIntent) },
                        onFindChannelNavigate = { startActivity(navigateToFindChannelIntent) },
                        onRegisterNavigate = {
                            startActivity(navigateToRegisterIntent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}