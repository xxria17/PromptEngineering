package com.dhxxn17.aichatapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/* 내부 저장 데이터 */
@Entity(tableName = "message_table")
data class Message(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "message_role")
    val role: String,
    @ColumnInfo(name = "message_content")
    val content: String,
    val historyId: Int
)