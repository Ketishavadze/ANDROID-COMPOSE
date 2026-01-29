package com.example.compose.ui.screen.home.contract

import com.example.compose.domain.model.Post
import com.example.compose.domain.model.Story

data class HomeState(
    val isLoading: Boolean = false,
    val stories: List<Story> = emptyList(),
    val posts: List<Post> = emptyList()
)