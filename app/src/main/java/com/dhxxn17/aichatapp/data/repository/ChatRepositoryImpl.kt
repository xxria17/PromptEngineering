package com.dhxxn17.aichatapp.data.repository

import com.dhxxn17.aichatapp.data.entity.ErrorResponse
import com.dhxxn17.aichatapp.data.entity.Messages
import com.dhxxn17.aichatapp.data.entity.RequestBody
import com.dhxxn17.aichatapp.data.entity.ResponseData
import com.dhxxn17.aichatapp.data.remote.ApiService
import com.dhxxn17.aichatapp.data.remote.NetworkResponse
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): ChatRepository {
    override suspend fun fetchChat(messages: Messages): NetworkResponse<ResponseData, ErrorResponse> {
        return apiService.fetchChat(
            body = RequestBody(
                messages = arrayListOf(messages)
            )
        )
    }
}