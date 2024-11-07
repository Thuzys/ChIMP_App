package com.example.chimp.screens.findChannel.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chimp.screens.about.activity.AboutActivity
import com.example.chimp.screens.chats.activity.ChatsActivity
import com.example.chimp.ui.composable.MenuBottomBar
import com.example.chimp.screens.findChannel.screen.ChIMPCommunityScreen
import com.example.chimp.ui.theme.ChIMPTheme

class FindChannelActivity: ComponentActivity() {

    private val navigateToAboutIntent by lazy {
        Intent(this, AboutActivity::class.java)
    }

    private val navigateToChatsIntent by lazy {
        Intent(this, ChatsActivity::class.java)
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
                            addChannelIsEnable = false,
                            aboutClick = { startActivity(navigateToAboutIntent) },
                            onMenuClick = { startActivity(navigateToChatsIntent) },
                        )
                    }
                ) { innerPadding ->
                    ChIMPCommunityScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}