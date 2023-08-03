package com.dhxxn17.aichatapp.data.repository

import com.dhxxn17.aichatapp.data.entity.Chat
import com.dhxxn17.aichatapp.data.entity.ErrorResponse
import com.dhxxn17.aichatapp.data.entity.History
import com.dhxxn17.aichatapp.data.entity.Message
import com.dhxxn17.aichatapp.data.entity.ResponseData
import com.dhxxn17.aichatapp.data.remote.NetworkResponse

interface ChatRepository {
    /* ai에게 대화 전송 */
    suspend fun sendChat(chat: List<Chat>): NetworkResponse<ResponseData, ErrorResponse>

    /* 이전 대화 내역 목록 불러 오기 */
    suspend fun getHistoryList(): List<History>

    /* 이전 대화 내역 불러 오기 */
    suspend fun getMessageList(id: Int): List<Message>

    /* 대화 제목 내부 디비에 저장 */
    suspend fun saveChatData(data: History)

    /* 대화 내역 내부 디비에 저장 */
    suspend fun saveMessage(data: Message)

    /* 대화 제목 삭제 */
    suspend fun deleteChatData(id: Int)

    /* 대화 내역 삭제 */
    suspend fun deleteMessagesByChatDataId(id: Int)

}