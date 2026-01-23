package com.example.compose.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("id")
    val id: Int,

    @SerialName("order_number")
    val orderNumber: String,

    @SerialName("date")
    val date: String,

    @SerialName("tracking_number")
    val trackingNumber: String,

    @SerialName("quantity")
    val quantity: Int,

    @SerialName("subtotal")
    val subtotal: Double,

    @SerialName("status")
    val status: String
)
