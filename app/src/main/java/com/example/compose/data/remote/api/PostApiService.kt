package com.example.compose.data.remote.api

import com.example.compose.data.remote.model.PostDto
import retrofit2.http.GET

interface PostApiService {

    @GET("post")  // Replace with your actual endpoint
    suspend fun getPosts(): List<PostDto>
}