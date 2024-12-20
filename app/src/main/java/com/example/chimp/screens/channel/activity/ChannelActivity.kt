package com.example.chimp.screens.channel.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chimp.application.ChIMPApplication
import com.example.chimp.screens.channel.screen.ChIMPChannelScreen
import com.example.chimp.screens.channel.viewModel.ChannelViewModel
import com.example.chimp.screens.channel.viewModel.ChannelViewModelFactory
import com.example.chimp.screens.channels.activity.ChannelsActivity
import com.example.chimp.screens.ui.theme.ChIMPTheme

class ChannelActivity : ComponentActivity() {

    private val viewModel by viewModels<ChannelViewModel>(
        factoryProducer = {
            ChannelViewModelFactory(
                service = (application as ChIMPApplication).channelService,
                userInfoRepo = (application as ChIMPApplication).userInfoRepository,
                channelRepo = (application as ChIMPApplication).channelRepository
            )
        }
    )

    private val navigateToChannelsIntent: Intent by lazy {
        Intent(this, ChannelsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ChIMPChannelScreen(
                        modifier = Modifier.padding(innerPadding),
                        vm = viewModel,
                    ) {
                        startActivity(navigateToChannelsIntent)
                        finish()
                    }
                }
            }
        }
    }
}