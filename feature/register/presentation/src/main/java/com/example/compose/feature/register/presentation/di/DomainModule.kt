package com.example.compose.feature.register.presentation.di

import com.example.compose.feature.register.domain.validation.ValidateRegisterFields
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideValidator() = ValidateRegisterFields()
}