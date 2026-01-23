package com.example.compose.domain.repository


import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus

interface OrdersRepository {
    suspend fun getOrders(): List<Order>
    suspend fun refreshOrders(): List<Order>
    suspend fun getOrderById(id: String): Order
    suspend fun updateStatus(id: String, newStatus: OrderStatus): Order
}
