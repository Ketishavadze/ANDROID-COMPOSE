package com.example.compose.data.remote.mapper

import com.example.compose.data.remote.dto.OrderDto
import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

private fun String.toUtcMillisOrZero(): Long =
    runCatching { dateFormat.parse(this)?.time ?: 0L }.getOrDefault(0L)

fun OrderDto.toDomain(): Order =
    Order(
        id = id.toString(),
        orderNumber = orderNumber,
        trackingNumber = trackingNumber,
        quantity = quantity,
        subtotal = subtotal,
        dateMillis = date.toUtcMillisOrZero(),
        status = OrderStatus.valueOf(status)
    )
