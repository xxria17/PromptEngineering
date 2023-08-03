package com.dhxxn17.aichatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dhxxn17.aichatapp.data.ChatListConverters
import com.dhxxn17.aichatapp.data.entity.ChatData
import com.dhxxn17.aichatapp.data.entity.Messages

@Database(entities = [ChatData::class, Messages::class], version = 1, exportSchema = false)
@TypeConverters(ChatListConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao

    companion object {
        const val DB_NAME = "Database_Chat"
    }
}