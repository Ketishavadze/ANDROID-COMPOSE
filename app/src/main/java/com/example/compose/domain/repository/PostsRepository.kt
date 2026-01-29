package com.example.compose.domain.repository

import com.example.compose.common.Resource
import com.example.compose.domain.model.Post

interface PostsRepository {
    suspend fun getPosts(): Resource<List<Post>>
}