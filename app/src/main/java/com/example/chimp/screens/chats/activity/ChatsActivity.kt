package com.example.chimp.screens.chats.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.chimp.application.DependenciesContainer
import com.example.chimp.screens.about.activity.AboutActivity
import com.example.chimp.screens.findChannel.activity.FindChannelActivity
import com.example.chimp.screens.ui.composable.MenuBottomBar
import com.example.chimp.screens.chats.screen.ChIMPChatsScreen
import com.example.chimp.screens.chats.viewModel.ChatsViewModel
import com.example.chimp.screens.ui.theme.ChIMPTheme

const val TAG = "CHIMP"

/**
 * ChatsActivity is the activity that displays the chats screen.
 *
 * This activity is responsible for setting up the chats screen and handling navigation to other
 * activities.
 */
class ChatsActivity : ComponentActivity() {

    private val viewModel by viewModels<ChatsViewModel>(
//        factoryProducer = {
//            ChatsViewModelFactory((application as DependenciesContainer).chatsService)
//        }
    )

    private val navigateToAboutIntent: Intent by lazy {
        Intent(this, AboutActivity::class.java)
    }

    private val navigateToFindChannelIntent: Intent by lazy {
        Intent(this, FindChannelActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChIMPTheme {
                ChIMPChatsScreen(
                    onFindChannelNavigate = { startActivity(navigateToFindChannelIntent) },
                    onAboutNavigate = { startActivity(navigateToAboutIntent) },
                    vm = viewModel
                )
            }
        }
    }
}
