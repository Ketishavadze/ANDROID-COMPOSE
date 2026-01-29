package com.example.compose.domain.repository

import com.example.compose.common.Resource
import com.example.compose.domain.model.Story

interface StoriesRepository {
    suspend fun getStories(): Resource<List<Story>>
}