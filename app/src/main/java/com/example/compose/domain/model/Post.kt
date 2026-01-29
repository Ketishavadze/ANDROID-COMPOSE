package com.example.compose.domain.model

data class Post(
    val id: Int,
    val avatar: String,
    val postDate: Long,
    val fullName: String,
    val images: List<String>,
    val commentsCount: Int,
    val likesCount: Int,
    val postDesc: String,
    val canComment: Boolean,
    val canPostPhoto: Boolean,
    val isLiked: Boolean = false
)