package com.example.compose.di

import com.example.compose.data.repository.PostsRepositoryImpl
import com.example.compose.data.repository.StoriesRepositoryImpl
import com.example.compose.domain.repository.PostsRepository
import com.example.compose.domain.repository.StoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostsRepository(
        impl: PostsRepositoryImpl
    ): PostsRepository

    @Binds
    @Singleton
    abstract fun bindStoriesRepository(
        impl: StoriesRepositoryImpl
    ): StoriesRepository
}