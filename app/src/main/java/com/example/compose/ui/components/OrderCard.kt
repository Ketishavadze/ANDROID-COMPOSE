package com.example.compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.domain.model.Order
import com.example.compose.domain.model.OrderStatus
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
        OrderStatus.PENDING -> Color(0xFFFF8A00)
        OrderStatus.DELIVERED -> Color(0xFF2E7D32)
        OrderStatus.CANCELED -> Color(0xFFC62828)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // ✅ completely white
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp // ✅ shadow on edges
        )
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Order #${order.orderNumber}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                // date right top
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF8A8A8A)
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = "Tracking number:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF8A8A8A)
                    )
                    Text(
                        text = order.trackingNumber,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Quantity: ${order.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF8A8A8A)
                )
                Text(
                    text = "Subtotal: $${order.subtotal}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = order.status.name,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = statusColor // ✅ orange/green/red
                )

                OutlinedButton(
                    onClick = onDetails,
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
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
