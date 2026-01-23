package com.example.compose.di

import com.example.compose.domain.repository.OrdersRepository
import com.example.compose.domain.usecase.GetOrderByIdUseCase
import com.example.compose.domain.usecase.GetOrdersUseCase
import com.example.compose.domain.usecase.RefreshOrdersUseCase
import com.example.compose.domain.usecase.UpdateOrderStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetOrdersUseCase(repo: OrdersRepository) = GetOrdersUseCase(repo)

    @Provides
    @Singleton
    fun provideRefreshOrdersUseCase(repo: OrdersRepository) = RefreshOrdersUseCase(repo)

    @Provides
    @Singleton
    fun provideGetOrderByIdUseCase(repo: OrdersRepository) = GetOrderByIdUseCase(repo)

    @Provides
    @Singleton
    fun provideUpdateOrderStatusUseCase(repo: OrdersRepository) = UpdateOrderStatusUseCase(repo)
}
