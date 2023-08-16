package com.dhxxn17.aichatapp.data.entity

import com.google.gson.annotations.SerializedName

/* 통신 응답 데이터 */
data class Chat(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

enum class ROLE(
    val text: String
) {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system")
}