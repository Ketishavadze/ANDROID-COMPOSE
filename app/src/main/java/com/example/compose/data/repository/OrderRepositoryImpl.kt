package com.example.compose.data.repository

import com.example.compose.data.remote.OrdersApi
import com.example.compose.data.remote.mapper.toDomain
import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus
import com.example.compose.domain.repository.OrdersRepository
import javax.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


class OrdersRepositoryImpl @Inject constructor(
    private val api: OrdersApi
) : OrdersRepository {

    private val mutex = Mutex()
    private var cached: List<Order> = emptyList()
    override suspend fun getOrders(): List<Order> =
        api.getOrders().map { it.toDomain() }

    override suspend fun refreshOrders(): List<Order> = getOrders()

    override suspend fun getOrderById(id: String): Order = mutex.withLock {
        cached.firstOrNull { it.id == id }?.let { return it }

        val remote = api.getOrders().map { it.toDomain() }
        cached = remote
        remote.first { it.id == id }
    }

    override suspend fun updateStatus(id: String, newStatus: OrderStatus): Order = mutex.withLock {
        if (cached.isEmpty()) {
            cached = api.getOrders().map { it.toDomain() }
        }

        val current = cached.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("Order not found: $id")

        val updated = current.copy(status = newStatus)

        cached = cached.map { if (it.id == id) updated else it }
        updated
    }

}
