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
import androidx.compose.ui.Modifier
import com.example.chimp.application.DependenciesContainer
import com.example.chimp.screens.channels.activity.ChannelsActivity
import com.example.chimp.screens.findChannel.screen.ChIMPFindChannelScreen
import com.example.chimp.screens.findChannel.viewModel.FindChannelVMFactory
import com.example.chimp.screens.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.screens.about.activity.AboutActivity
import com.example.chimp.screens.register.activity.RegisterActivity
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.ui.composable.SearchBar
import com.example.chimp.screens.ui.theme.ChIMPTheme

/**
 * The activity that represents the find channel screen.
 */
class FindChannelActivity: ComponentActivity() {
    private val viewModel by viewModels<FindChannelViewModel>(
        factoryProducer = {
            FindChannelVMFactory(
                (application as DependenciesContainer).findChannelService,
                (application as DependenciesContainer).userInfoRepository
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                    ChIMPFindChannelScreen(
                        viewModel = viewModel,
                        onAboutNavigate = { startActivity(navigateToAboutIntent) },
                        onChatsNavigate = { startActivity(navigateToChatsIntent) },
                        onRegisterNavigate = {
                            startActivity(navigateToRegisterIntent)
                            finish()
                        }
                    )
            }
        }
    }
}