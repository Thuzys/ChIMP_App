package com.example.chimp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chimp.ui.screen.ChIMPScreen
import com.example.chimp.ui.theme.ChIMPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChIMPScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}