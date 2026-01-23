package com.example.compose.data.remote

import com.example.compose.data.remote.dto.OrderDto
import retrofit2.http.GET

interface OrdersApi {
    @GET("orders")
    suspend fun getOrders(): List<OrderDto>

}
