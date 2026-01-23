package com.example.compose.domain.repository

import com.example.compose.domain.model.Chat

interface ChatsRepository {
    suspend fun getChats(): List<Chat>
}
