package com.dhxxn17.aichatapp.data

import androidx.room.TypeConverter
import com.dhxxn17.aichatapp.data.entity.Message
import com.google.gson.Gson

class ChatListConverters {
    @TypeConverter
    fun listToJson(value: List<Message>): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Message>? {
        return Gson().fromJson(value, Array<Message>::class.java)?.toList()
    }
}