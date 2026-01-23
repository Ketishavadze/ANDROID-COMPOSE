package com.example.compose.data.remote.mapper


import com.example.compose.data.remote.dto.ChatDto
import com.example.compose.domain.model.Chat
import com.example.compose.domain.model.MessageType

fun ChatDto.toDomain(): Chat =
    Chat(
        id = id.toString(),
        imageUrl = image?.takeIf { it.isNotBlank() },
        owner = owner,
        lastMessage = lastMessage,
        lastActive = lastActive,
        unreadMessages = unreadMessages,
        isTyping = isTyping,
        lastMessageType = MessageType.fromApi(lastMessageType)
    )
