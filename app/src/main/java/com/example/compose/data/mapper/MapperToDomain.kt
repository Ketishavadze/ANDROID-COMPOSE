package com.example.compose.data.mapper

import com.example.compose.data.remote.model.PostDto
import com.example.compose.data.remote.model.StoryDto
import com.example.compose.domain.model.Post
import com.example.compose.domain.model.Story

fun StoryDto.toDomain() = Story(
    id = id,
    title = title,
    cover = cover
)

fun PostDto.toDomain() = Post(
    id = id,
    avatar = avatar,
    postDate = postDate,
    fullName = "$firstName $lastName",
    images = images,
    commentsCount = commentsCount,
    likesCount = likesCount,
    postDesc = postDesc ?: "",
    canComment = canComment,
    canPostPhoto = canPostPhoto
)