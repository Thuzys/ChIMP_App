package com.example.chimp.screens.register.activity

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
import com.example.chimp.screens.channels.activity.ChannelsActivity
import com.example.chimp.screens.register.screen.ChIMPLoginScreen
import com.example.chimp.screens.register.viewModel.RegisterViewModel
import com.example.chimp.screens.register.viewModel.LoginViewModelFactory
import com.example.chimp.screens.ui.theme.ChIMPTheme

/**
 * The activity that represents the login and signIn screen.
 */
class RegisterActivity: ComponentActivity() {
    private val viewModel by viewModels<RegisterViewModel>(
        factoryProducer = {
            LoginViewModelFactory(
                (application as DependenciesContainer).loginService,
                (application as DependenciesContainer).formValidation,
                (application as DependenciesContainer).userInfoRepository
            )
        }
    )

    private val navigateToChatsIntent by lazy {
        Intent(this, ChannelsActivity::class.java)
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