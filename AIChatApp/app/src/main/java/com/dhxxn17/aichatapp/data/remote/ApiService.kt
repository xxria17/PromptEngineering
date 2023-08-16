package com.dhxxn17.aichatapp.data.remote

import com.dhxxn17.aichatapp.data.entity.ErrorResponse
import com.dhxxn17.aichatapp.data.entity.RequestBody
import com.dhxxn17.aichatapp.data.entity.ResponseData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("v1/chat/completions")
    suspend fun sendChat(
        @Header("Authorization") apiKey: String = "",
        @Header("Content-Type") contentType: String = "application/json",
        @Body body: RequestBody
    ): NetworkResponse<ResponseData, ErrorResponse>
}