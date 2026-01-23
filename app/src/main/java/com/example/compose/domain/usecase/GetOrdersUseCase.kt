package com.example.compose.domain.usecase

import com.example.compose.domain.model.Order
import com.example.compose.domain.repository.OrdersRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val repo: OrdersRepository) {
    suspend operator fun invoke(): List<Order> = repo.getOrders()
}