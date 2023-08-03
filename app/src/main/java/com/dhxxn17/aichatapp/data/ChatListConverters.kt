package com.dhxxn17.aichatapp.data

import androidx.room.TypeConverter
import com.dhxxn17.aichatapp.data.entity.Messages
import com.google.gson.Gson

class ChatListConverters {
    @TypeConverter
    fun listToJson(value: List<Messages>): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Messages>? {
        return Gson().fromJson(value, Array<Messages>::class.java)?.toList()
    }
}