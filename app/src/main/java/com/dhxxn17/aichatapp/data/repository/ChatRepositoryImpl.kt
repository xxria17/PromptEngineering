package com.dhxxn17.aichatapp.data.repository

import com.dhxxn17.aichatapp.data.entity.ChatData
import com.dhxxn17.aichatapp.data.entity.ChatDataWithMessages
import com.dhxxn17.aichatapp.data.entity.ErrorResponse
import com.dhxxn17.aichatapp.data.entity.Messages
import com.dhxxn17.aichatapp.data.entity.RequestBody
import com.dhxxn17.aichatapp.data.entity.ResponseData
import com.dhxxn17.aichatapp.data.local.ChatDao
import com.dhxxn17.aichatapp.data.remote.ApiService
import com.dhxxn17.aichatapp.data.remote.NetworkResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: ChatDao
): ChatRepository {
    override suspend fun fetchChat(messages: Messages): NetworkResponse<ResponseData, ErrorResponse> {
        return apiService.fetchChat(
            body = RequestBody(
                messages = arrayListOf(messages)
            )
        )
    }

    override suspend fun requestChatList(): List<ChatData> {
        return dao.getChatList()
    }

    override suspend fun requestChatHistory(id: Long): ChatDataWithMessages {
        return dao.getChatHistory(id)
    }

    override suspend fun saveChatData(data: ChatData) {
        return dao.saveChat(data)
    }

    override suspend fun saveMessage(data: List<Messages>) {
        return dao.saveMessages(data)
    }

    override suspend fun deleteChatData(id: Long) {
        return dao.deleteChatData(id)
    }

    override suspend fun deleteMessagesByChatDataId(id: Long) {
        return dao.deleteMessagesByChatDataID(id)
    }


    override suspend fun updateMessages(data: List<Messages>) {
        return dao.updateMessageData(data)
    }
}