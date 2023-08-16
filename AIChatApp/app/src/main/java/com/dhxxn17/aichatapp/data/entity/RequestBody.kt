package com.dhxxn17.aichatapp.data.entity

data class RequestBody(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Chat>
)