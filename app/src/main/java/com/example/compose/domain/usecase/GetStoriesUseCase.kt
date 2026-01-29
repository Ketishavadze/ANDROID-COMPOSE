package com.example.compose.domain.usecase

import com.example.compose.common.Resource
import com.example.compose.domain.model.Story
import com.example.compose.domain.repository.StoriesRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repo: StoriesRepository
) {
    suspend operator fun invoke(): Resource<List<Story>> = repo.getStories()
}