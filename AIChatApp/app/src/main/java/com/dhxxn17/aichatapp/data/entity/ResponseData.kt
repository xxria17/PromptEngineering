package com.dhxxn17.aichatapp.data.entity

import com.google.gson.annotations.SerializedName

data class ResponseData(
    val id: String,
    @SerializedName("object")
    val aiType: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("created")
    val createdTime: Long,
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("usage")
    val usage: Usage
)

data class Choice(
    val index: Int,
    @SerializedName("message")
    val messages: Chat,
    @SerializedName("finish_reason")
    val isFinish: String
)

data class Usage(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("total_tokens")
    val totalTokens: Int
)