package com.dhxxn17.helpyou.data.network

import com.dhxxn17.helpyou.data.model.ChatResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkApiService {

    @GET("message")
    suspend fun sendChat(
        @Header("Authorization") apiKet: String = "",
        @Query(value = "v") date: String,
        @Query(value = "q") message: String
    ): ChatResponse

}