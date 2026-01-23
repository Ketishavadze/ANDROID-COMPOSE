package com.example.compose.data.repository

import com.example.compose.data.remote.api.ChatsApi
import com.example.compose.data.remote.mapper.toDomain
import com.example.compose.domain.model.Chat
import com.example.compose.domain.repository.ChatsRepository
import javax.inject.Inject

class ChatsRepositoryImpl @Inject constructor(
    private val api: ChatsApi
) : ChatsRepository {
    override suspend fun getChats(): List<Chat> =
        api.getChats().map { it.toDomain() }
}
