package com.example.compose.data.remote.api

import com.example.compose.data.remote.dto.ChatDto
import retrofit2.http.GET

interface ChatsApi {
    @GET("chats")
    suspend fun getChats(): List<ChatDto>
}
