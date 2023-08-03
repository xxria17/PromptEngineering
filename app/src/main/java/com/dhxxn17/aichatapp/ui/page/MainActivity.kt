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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { _innerPadding ->
        Box(
            modifier = Modifier.padding(_innerPadding)
        ) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.IntroScreen.route) {
        composable(Screens.ChatListScreen.route) {
            ChatListScreen(navController)
        }
        composable(
            Screens.ChatScreen.route,
            arguments = listOf(
                navArgument(ID_ARGS) {
                    type = NavType.StringType
                }
            )
        ) { _backStackEntry ->
            val id = _backStackEntry.arguments!!.getString(ID_ARGS)!!
            ChatScreen(navController, id)
        }
        composable(Screens.IntroScreen.route) {
            IntroScreen(navController)
        }
    }
}
