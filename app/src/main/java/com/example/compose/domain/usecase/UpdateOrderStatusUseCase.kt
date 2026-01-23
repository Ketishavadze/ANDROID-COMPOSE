package com.example.compose.domain.usecase

import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus
import com.example.compose.domain.repository.OrdersRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(private val repo: OrdersRepository) {
    suspend operator fun invoke(id: String, status: OrderStatus): Order = repo.updateStatus(id, status)
}