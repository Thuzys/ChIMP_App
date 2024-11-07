package com.example.chimp.screens.login.activity

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
import com.example.chimp.screens.chats.activity.ChatsActivity
import com.example.chimp.screens.login.screen.ChIMPLoginScreen
import com.example.chimp.screens.login.viewModel.LoginViewModel
import com.example.chimp.screens.login.viewModel.LoginViewModelFactory
import com.example.chimp.ui.theme.ChIMPTheme

class LoginActivity: ComponentActivity() {
    private val viewModel by viewModels<LoginViewModel>(
        factoryProducer = {
            LoginViewModelFactory((application as DependenciesContainer).loginService)
        }
    )

    private val navigateToChatsIntent by lazy {
        Intent(this, ChatsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChIMPTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    ChIMPLoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                        onLogin = { startActivity(navigateToChatsIntent) }
                    )
                }
            }
        }
    }
}