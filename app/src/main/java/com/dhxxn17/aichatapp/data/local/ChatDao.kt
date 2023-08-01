package com.dhxxn17.aichatapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dhxxn17.aichatapp.data.entity.ChatData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ChatDao {

    @Query(value = "SELECT * FROM ChatData")
    fun getChatList(): List<ChatData>

    @Insert
    fun saveChat(data: ChatData)

    @Delete
    fun delete(data: ChatData)
}