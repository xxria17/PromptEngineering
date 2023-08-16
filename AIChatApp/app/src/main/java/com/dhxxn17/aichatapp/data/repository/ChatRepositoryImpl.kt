package com.dhxxn17.aichatapp.data.repository

import com.dhxxn17.aichatapp.data.entity.Chat
import com.dhxxn17.aichatapp.data.entity.ErrorResponse
import com.dhxxn17.aichatapp.data.entity.History
import com.dhxxn17.aichatapp.data.entity.Message
import com.dhxxn17.aichatapp.data.entity.RequestBody
import com.dhxxn17.aichatapp.data.entity.ResponseData
import com.dhxxn17.aichatapp.data.local.HistoryDao
import com.dhxxn17.aichatapp.data.local.MessageDao
import com.dhxxn17.aichatapp.data.remote.ApiService
import com.dhxxn17.aichatapp.data.remote.NetworkResponse
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val historyDao: HistoryDao,
    private val messageDao: MessageDao
): ChatRepository {

    override suspend fun sendChat(chat: List<Chat>): NetworkResponse<ResponseData, ErrorResponse> {
        return apiService.sendChat(body = RequestBody(
            messages = chat
        ))
    }

    override suspend fun getHistoryList(): List<History> {
        return historyDao.getAllHistories()
    }

    override suspend fun getMessageList(id: Int): List<Message> {
        return messageDao.getMessageByHistoryId(id)
    }

    override suspend fun saveChatData(data: History): Long {
        return historyDao.insertHistory(data)
    }

    override suspend fun saveMessage(data: Message) {
        return messageDao.insertMessage(data)
    }

    override suspend fun deleteChatData(id: Int) {
        return historyDao.deleteHistory(id)
    }

    override suspend fun deleteMessagesByChatDataId(id: Int) {
        return messageDao.deleteMessagesByChatDataID(id)
    }

}