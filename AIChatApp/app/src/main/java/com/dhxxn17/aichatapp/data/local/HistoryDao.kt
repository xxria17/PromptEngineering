package com.dhxxn17.aichatapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dhxxn17.aichatapp.data.entity.History

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistory(history: History): Long

    @Query("SELECT * FROM history_table")
    suspend fun getAllHistories(): List<History>

    @Query("DELETE FROM history_table WHERE id = :chatDataId")
    suspend fun deleteHistory(chatDataId: Int)
}