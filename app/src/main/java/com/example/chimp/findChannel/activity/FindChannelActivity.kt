package com.example.chimp.findChannel.activity

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
import com.example.chimp.chats.activity.ChatsActivity
import com.example.chimp.findChannel.screen.ChIMPFindChannelScreen
import com.example.chimp.findChannel.viewModel.FindChannelVMFactory
import com.example.chimp.findChannel.viewModel.FindChannelViewModel
import com.example.chimp.ui.theme.ChIMPTheme

class FindChannelActivity: ComponentActivity() {
    private val viewModel by viewModels<FindChannelViewModel>(
        factoryProducer = {
            FindChannelVMFactory((application as DependenciesContainer).findChannelService)
        }
    )

    private val navigateToChannelIntent by lazy {
        Intent(this, ChatsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChIMPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ChIMPFindChannelScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        onJoinChannel = { startActivity(navigateToChannelIntent) }
                    )
                }
            }
        }
    }
}