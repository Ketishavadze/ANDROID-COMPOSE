package com.example.compose.domain.model

data class Chat(
    val id: String,
    val imageUrl: String?,
    val owner: String,
    val lastMessage: String,
    val lastActive: String,
    val unreadMessages: Int,
    val isTyping: Boolean,
    val lastMessageType: MessageType
)
