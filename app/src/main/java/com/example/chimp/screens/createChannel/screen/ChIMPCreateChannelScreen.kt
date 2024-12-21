package com.example.chimp.screens.createChannel.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.chimp.screens.createChannel.viewModel.CreateChannelViewModel
import com.example.chimp.screens.ui.composable.MenuBottomBar


const val CREATE_CHANNEL_SCREEN_TAG = "CreateChannelScreen"

@Composable
fun ChIMPCreateChannelScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateChannelViewModel,
    onAboutNavigate: () -> Unit,
    onChatsNavigate: () -> Unit,
    onFindChannelNavigate: () -> Unit,
    onRegisterNavigate: () -> Unit
) {


//    Column(
//        modifier = modifier
//    ) {
//        val curr = viewModel.state.collectAsState().value
//        Log.i(CREATE_CHANNEL_SCREEN_TAG, "State: ${curr::class.simpleName}")
//        FindChannelScreenAux(
//            bottomBar = {
//                MenuBottomBar(
//                    findChannelIsEnable = false,
//                    onMenuClick = onChatsNavigate,
//                    aboutClick = onAboutNavigate
//                )
//            }
//        ) { modifier ->
//
//            when(curr) {
//
//                }
//            }
//        }
//    }
}

@Composable
private fun FindChannelScreenAux(
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        bottomBar = bottomBar
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}