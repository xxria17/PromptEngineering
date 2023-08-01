package com.dhxxn17.aichatapp.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dhxxn17.aichatapp.ui.page.chat.ChatScreen
import com.dhxxn17.aichatapp.ui.page.intro.IntroScreen
import com.dhxxn17.aichatapp.ui.page.list.ChatListScreen
import com.dhxxn17.aichatapp.ui.theme.AIChatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIChatAppTheme {
                AiChatApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatApp() {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { _innerPadding ->
        Box(
            modifier = Modifier.padding(_innerPadding)
        ) {
            ChatScreen()
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.IntroScreen.route) {
        composable(Screens.ChatListScreen.route) {
            ChatListScreen(navController)
        }
        composable(Screens.ChatScreen.route) {
            ChatScreen()
        }
        composable(Screens.IntroScreen.route) {
            IntroScreen(navController)
        }
    }
}
