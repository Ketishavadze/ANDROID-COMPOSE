package com.example.compose.data.repository

import com.example.compose.common.HandleResource
import com.example.compose.common.Resource
import com.example.compose.data.mapper.toDomain
import com.example.compose.data.remote.api.StoriesApiService
import com.example.compose.data.remote.model.StoryDto
import com.example.compose.domain.model.Story
import com.example.compose.domain.repository.StoriesRepository
import javax.inject.Inject

class StoriesRepositoryImpl @Inject constructor(
    private val api: StoriesApiService,
    private val handle: HandleResource
) : StoriesRepository {

    override suspend fun getStories(): Resource<List<Story>> {
        val result = handle.safeApiCall<List<StoryDto>> {
            api.getStories()
        }

        return when (result) {
            is Resource.Success -> {
                val domainStories = result.data?.map { it.toDomain() } ?: emptyList()
                Resource.Success(domainStories)
            }
            is Resource.Error -> Resource.Error(result.errorMessage)
            else -> Resource.Error("Unknown error")
        }
    }
}