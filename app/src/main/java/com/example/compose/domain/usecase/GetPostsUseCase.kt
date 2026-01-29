package com.example.compose.domain.usecase

import com.example.compose.common.Resource
import com.example.compose.domain.model.Post
import com.example.compose.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repo: PostsRepository
) {
    suspend operator fun invoke(): Resource<List<Post>> = repo.getPosts()
}