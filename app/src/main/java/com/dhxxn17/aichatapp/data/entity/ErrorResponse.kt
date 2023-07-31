package com.dhxxn17.aichatapp.data.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("param")
    val param: String,
    @SerializedName("code")
    val code: Int
)