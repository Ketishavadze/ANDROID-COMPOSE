package com.example.compose.di

import com.example.compose.data.repository.ChatsRepositoryImpl
import com.example.compose.domain.repository.ChatsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindChatsRepository(impl: ChatsRepositoryImpl): ChatsRepository
}
