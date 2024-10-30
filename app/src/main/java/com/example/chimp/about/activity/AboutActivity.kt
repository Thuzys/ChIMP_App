package com.example.chimp.activity

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
import com.example.chimp.ui.composable.MenuBottomBar
import com.example.chimp.ui.screen.ChIMPAboutScreen
import com.example.chimp.ui.theme.ChIMPTheme
import com.example.chimp.viewModel.AboutViewModel

class AboutActivity: ComponentActivity() {
    private val viewModel by viewModels<AboutViewModel>()

    private val navigateToChatsIntent by lazy {
        Intent(this, ChatsActivity::class.java)
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
                            addChannelClick = { startActivity(navigateToFindChannelIntent) },
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