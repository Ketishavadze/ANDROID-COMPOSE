package com.example.compose.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    // Use cases are automatically provided by Hilt through @Inject constructor
    // This module can be used for custom use case providers if needed
}