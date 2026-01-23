package com.example.compose.domain.model

data class Order(
    val id: String,
    val orderNumber: String,
    val trackingNumber: String,
    val quantity: Int,
    val subtotal: Double,
    val dateMillis: Long,
    val status: OrderStatus
)
