package com.example.compose.domain.model


enum class MessageType {
    TEXT, FILE, VOICE;

    companion object {
        fun fromApi(value: String): MessageType =
            when (value.lowercase()) {
                "text" -> TEXT
                "file" -> FILE
                "voice" -> VOICE
                else -> TEXT
            }
    }
}
