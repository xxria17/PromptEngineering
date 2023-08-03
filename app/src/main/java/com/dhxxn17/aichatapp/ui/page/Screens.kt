package com.dhxxn17.aichatapp.ui.page

const val ID_ARGS = "ID"
sealed class Screens(
    val route: String
) {
    object ChatScreen: Screens("chat_screen/{$ID_ARGS}") {
        fun withId(id: String): String {
            return this.route.replace(ID_ARGS, id)
        }
    }

    object ChatListScreen: Screens("chat_list_screen")

    object IntroScreen: Screens("intro_screen")
}