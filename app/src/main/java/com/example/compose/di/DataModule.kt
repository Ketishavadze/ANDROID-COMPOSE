package com.example.compose.di

import com.example.compose.data.remote.api.RegisterApiService
import com.example.compose.data.repository.RegisterRepositoryImpl
import com.example.compose.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRegisterRepository(
        api: RegisterApiService
    ): RegisterRepository = RegisterRepositoryImpl(api)
}
