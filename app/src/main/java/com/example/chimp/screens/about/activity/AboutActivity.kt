package com.example.chimp.screens.about.activity

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
import com.example.chimp.screens.channels.activity.ChannelsActivity
import com.example.chimp.screens.findChannel.activity.FindChannelActivity
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.about.screen.ChIMPAboutScreen
import com.example.chimp.screens.ui.theme.ChIMPTheme
import com.example.chimp.screens.about.viewModel.AboutViewModel

const val ABOUT_SCREEN_TAG = "AboutScreen"

/**
 * AboutActivity is the activity that displays the about screen.
 *
 * This activity is responsible for setting up the about screen and handling navigation to other
 * activities.
 */
class AboutActivity: ComponentActivity() {
    private val viewModel by viewModels<AboutViewModel>()

    private val navigateToChatsIntent by lazy {
        Intent(this, ChannelsActivity::class.java)
    }

    private val navigateToFindChannelIntent by lazy {
        Intent(this, FindChannelActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MenuBottomBar(
                            aboutIsEnable = false,
                            findChannelClick = { startActivity(navigateToFindChannelIntent) },
                            onMenuClick = { startActivity(navigateToChatsIntent) }
                        )
                    }
                ) { innerPadding ->
                    ChIMPAboutScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}