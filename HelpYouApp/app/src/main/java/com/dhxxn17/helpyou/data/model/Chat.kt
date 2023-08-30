package com.dhxxn17.helpyou.data.model

data class Chat(
    val message: String,
    val role: ROLE
)

enum class ROLE {
    USER,
    AI
}