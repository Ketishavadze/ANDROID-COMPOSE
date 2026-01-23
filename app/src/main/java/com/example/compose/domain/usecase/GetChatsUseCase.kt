package com.example.compose.domain.usecase


import com.example.compose.domain.model.Chat
import com.example.compose.domain.repository.ChatsRepository
import javax.inject.Inject

class GetChatsUseCase @Inject constructor(
    private val repo: ChatsRepository
) {
    suspend operator fun invoke(): List<Chat> = repo.getChats()
}
