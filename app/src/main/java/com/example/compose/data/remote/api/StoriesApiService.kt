package com.example.compose.data.remote.api

import com.example.compose.data.remote.model.StoryDto
import retrofit2.http.GET

interface StoriesApiService {

    @GET("story")  // Replace with your actual endpoint
    suspend fun getStories(): List<StoryDto>
}