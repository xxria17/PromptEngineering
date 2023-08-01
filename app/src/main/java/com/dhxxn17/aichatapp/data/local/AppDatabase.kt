package com.dhxxn17.aichatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dhxxn17.aichatapp.data.entity.ChatData

@Database(entities = [ChatData::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao

    companion object {
        const val DB_NAME = "Database_Chat"
    }
}