package com.dhxxn17.helpyou.data.repository

import com.dhxxn17.helpyou.data.model.ChatRequest
import com.dhxxn17.helpyou.data.model.ChatResponse
import com.dhxxn17.helpyou.data.network.NetworkApiService
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val apiService: NetworkApiService
): ChatRepository {

    override suspend fun sendChat(chat: ChatRequest): ChatResponse {
        return apiService.sendChat(
            date = chat.date,
            message = chat.message
        )
    }
}