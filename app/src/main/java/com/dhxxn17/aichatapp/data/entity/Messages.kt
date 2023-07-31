package com.dhxxn17.aichatapp.data.entity

import com.google.gson.annotations.SerializedName

data class Messages(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)
