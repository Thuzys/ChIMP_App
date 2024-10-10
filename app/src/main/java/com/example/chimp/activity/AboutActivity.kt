package com.example.chimp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chimp.ui.screen.ChIMPAboutScreen
import com.example.chimp.ui.theme.ChIMPTheme
import com.example.chimp.viewModel.AboutViewModel

class AboutActivity: ComponentActivity() {
    private val viewModel by viewModels<AboutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChIMPAboutScreen(
                        modifier = Modifier.padding(innerPadding)
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TODO("implement code to go back to main screen")
    }
}