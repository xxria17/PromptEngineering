package com.dhxxn17.helpyou.data.repository

import com.dhxxn17.helpyou.data.model.ChatRequest
import com.dhxxn17.helpyou.data.model.ChatResponse

interface ChatRepository {

    suspend fun sendChat(chat: ChatRequest): ChatResponse

}