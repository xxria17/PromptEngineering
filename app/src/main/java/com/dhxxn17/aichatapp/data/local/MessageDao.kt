package com.dhxxn17.aichatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dhxxn17.aichatapp.data.entity.Message

@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(message: Message)

    @Query("SELECT * FROM message_table WHERE historyId = :id")
    suspend fun getMessageByHistoryId(id: Int): List<Message>

    @Query("DELETE FROM message_table WHERE historyId = :chatDataId")
    fun deleteMessagesByChatDataID(chatDataId: Int)
}