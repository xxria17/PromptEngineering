package com.dhxxn17.aichatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dhxxn17.aichatapp.data.entity.ChatData
import com.dhxxn17.aichatapp.data.entity.ChatDataWithMessages
import com.dhxxn17.aichatapp.data.entity.Messages

@Dao
interface ChatDao {

    @Query(value = "SELECT * FROM chat_table")
    fun getChatList(): List<ChatData>

    @Insert
    fun saveChat(data: ChatData)

    @Insert
    fun saveMessages(data: List<Messages>)

    @Query("DELETE FROM chat_table WHERE id = :chatDataId")
    fun deleteChatData(chatDataId: Long)

    @Query("DELETE FROM messages WHERE chat_data_id = :chatDataId")
    fun deleteMessagesByChatDataID(chatDataId: Long)

    @Query(value = "SELECT * FROM chat_table WHERE id = :id")
    fun getChatHistory(id: Long): ChatDataWithMessages

    @Update
    fun updateMessageData(data: List<Messages>)
}