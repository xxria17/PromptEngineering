package com.dhxxn17.aichatapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChatDataWithMessages(
    @Embedded val chatData: ChatData,
    @Relation(parentColumn = "id", entityColumn = "chat_data_id")
    val messages: List<Messages>
)
