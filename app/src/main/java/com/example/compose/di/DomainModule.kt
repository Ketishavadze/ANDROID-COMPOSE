package com.example.compose.di

import com.example.compose.domain.validation.ValidateRegisterFields
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides @Singleton
    fun provideValidator() = ValidateRegisterFields()
}
