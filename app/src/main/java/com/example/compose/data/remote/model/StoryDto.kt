package com.example.compose.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val title: String,
    val cover: String
)