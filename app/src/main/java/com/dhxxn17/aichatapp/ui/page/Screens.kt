package com.dhxxn17.aichatapp.ui.page

sealed class Screens(
    val route: String
) {
    object ChatScreen: Screens("chat_screen/{ID}")

    object ChatListScreen: Screens("chat_list_screen")

    object IntroScreen: Screens("intro_screen")
}