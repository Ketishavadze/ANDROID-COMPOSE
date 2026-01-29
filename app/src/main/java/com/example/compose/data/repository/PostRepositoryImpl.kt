package com.example.compose.data.repository

import com.example.compose.common.HandleResource
import com.example.compose.common.Resource
import com.example.compose.data.mapper.toDomain
import com.example.compose.data.remote.api.PostApiService
import com.example.compose.data.remote.model.PostDto
import com.example.compose.domain.model.Post
import com.example.compose.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: PostApiService,
    private val handle: HandleResource
) : PostsRepository {

    override suspend fun getPosts(): Resource<List<Post>> {
        val result = handle.safeApiCall<List<PostDto>> {
            api.getPosts()
        }

        return when (result) {
            is Resource.Success -> {
                val domainPosts = result.data?.map { it.toDomain() } ?: emptyList()
                Resource.Success(domainPosts)
            }
            is Resource.Error -> Resource.Error(result.errorMessage)
            else -> Resource.Error("Unexpected error")
        }
    }
}