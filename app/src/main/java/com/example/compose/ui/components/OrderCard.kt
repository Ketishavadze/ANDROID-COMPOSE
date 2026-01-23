package com.example.compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus
import com.example.compose.ui.graphics.AppColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderCard(
    order: Order,
    onDetails: () -> Unit
) {
    val dateText = remember(order.dateMillis) {
        if (order.dateMillis == 0L) ""
        else SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(order.dateMillis))
    }

    val statusColor = when (order.status) {
        OrderStatus.PENDING -> AppColors.Pending
        OrderStatus.DELIVERED -> AppColors.Delivered
        OrderStatus.CANCELED -> AppColors.Canceled
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Order #${order.orderNumber}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextPrimary
                )

                Text(
                    text = dateText,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextSecondary
                )
            }

            Spacer(Modifier.height(12.dp))

            Column {
                Text(
                    text = "Tracking number:",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppColors.TextSecondary
                )
                Text(
                    text = order.trackingNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.TextPrimary
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Quantity: ${order.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.TextSecondary
                )
                Text(
                    text = "Subtotal: $${order.subtotal}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.TextPrimary
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = order.status.name,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = statusColor
                )

                OutlinedButton(
                    onClick = onDetails,
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AppColors.DetailsButtonBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = AppColors.DetailsButtonText
                    )
                ) {
                    Text("Details")
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderCardPreview() {
    MaterialTheme {
        OrderCard(
            order = Order(
                id = "1",
                orderNumber = "1524",
                trackingNumber = "IK287368838",
                quantity = 2,
                subtotal = 110.0,
                dateMillis = 1715558400000,
                status = OrderStatus.PENDING
            ),
            onDetails = {}
        )
    }
}
