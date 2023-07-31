package com.dhxxn17.aichatapp.data.repository

import com.dhxxn17.aichatapp.data.entity.ErrorResponse
import com.dhxxn17.aichatapp.data.entity.Messages
import com.dhxxn17.aichatapp.data.entity.ResponseData
import com.dhxxn17.aichatapp.data.remote.NetworkResponse

interface ChatRepository {
    suspend fun fetchChat(messages: Messages): NetworkResponse<ResponseData, ErrorResponse>
}