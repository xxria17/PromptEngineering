package com.dhxxn17.aichatapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity("messages")
data class Messages(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "role")
    @SerializedName("role")
    val role: String,

    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String,

    @Transient
    @ColumnInfo("chat_data_id")
    var chatDataId: Long = 0L
)
