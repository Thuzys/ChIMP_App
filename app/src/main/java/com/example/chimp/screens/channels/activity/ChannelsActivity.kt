package com.example.chimp.screens.channels.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.chimp.application.ChIMPApplication
import com.example.chimp.screens.about.activity.AboutActivity
import com.example.chimp.screens.channel.activity.ChannelActivity
import com.example.chimp.screens.findChannel.activity.FindChannelActivity
import com.example.chimp.screens.channels.screen.ChIMPChannelsScreen
import com.example.chimp.screens.channels.viewModel.ChannelsViewModel
import com.example.chimp.screens.channels.viewModel.ChannelsViewModelFactory
import com.example.chimp.screens.register.activity.RegisterActivity
import com.example.chimp.screens.ui.theme.ChIMPTheme

/**
 * ChannelsActivity is the activity that displays the channels screen.
 *
 * This activity is responsible for setting up the chats screen and handling navigation to other
 * activities.
 */
class ChannelsActivity : ComponentActivity() {

    private val viewModel by viewModels<ChannelsViewModel>(
        factoryProducer = {
            ChannelsViewModelFactory(
                service = (application as ChIMPApplication).channelsService,
                userInfoRepository = (application as ChIMPApplication).userInfoRepository,
                channelRepository = (application as ChIMPApplication).channelRepository
            )
        }
    )
    
    private val navigateToRegisterIntent: Intent by lazy {
        Intent(this, RegisterActivity::class.java)
    }

    private val navigateToAboutIntent: Intent by lazy {
        Intent(this, AboutActivity::class.java)
    }

    private val navigateToFindChannelIntent: Intent by lazy {
        Intent(this, FindChannelActivity::class.java)
    }

    private val navigateToChannelIntent: Intent by lazy {
        Intent(this, ChannelActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                ChIMPChannelsScreen(
                    onFindChannelNavigate = { startActivity(navigateToFindChannelIntent) },
                    onAboutNavigate = { startActivity(navigateToAboutIntent) },
                    onChannelNavigate = { startActivity(navigateToChannelIntent) },
                    vm = viewModel,
                    onRegisterNavigate =
                    {
                        startActivity(navigateToRegisterIntent)
                        finish()
                    },
                )
            }
        }
    }
}
