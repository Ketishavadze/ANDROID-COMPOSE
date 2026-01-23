package com.example.compose.di

import com.example.compose.data.repository.OrdersRepositoryImpl
import com.example.compose.domain.repository.OrdersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton
    abstract fun bindOrdersRepository(impl: OrdersRepositoryImpl): OrdersRepository
}
