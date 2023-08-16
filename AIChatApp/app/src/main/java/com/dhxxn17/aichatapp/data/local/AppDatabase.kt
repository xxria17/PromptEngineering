package com.dhxxn17.aichatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dhxxn17.aichatapp.data.ChatListConverters
import com.dhxxn17.aichatapp.data.entity.History
import com.dhxxn17.aichatapp.data.entity.Message

@Database(entities = [History::class, Message::class], version = 1, exportSchema = false)
@TypeConverters(ChatListConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun messageDao(): MessageDao

    companion object {
        const val DB_NAME = "Database_Chat"
    }
}