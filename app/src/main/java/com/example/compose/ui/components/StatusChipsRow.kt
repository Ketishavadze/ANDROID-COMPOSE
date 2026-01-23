package com.example.compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.domain.model.OrderStatus

@Composable
fun StatusChipsRow(
    selected: OrderStatus,
    onSelect: (OrderStatus) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OrderStatus.entries.forEach { status ->
                val isSelected = selected == status

                FilterChip(
                    selected = isSelected,
                    onClick = { onSelect(status) },
                    enabled = true,
                    label = {
                        Text(
                            text = status.name.lowercase().replaceFirstChar { it.uppercase() },
                            color = if (isSelected)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary, // make primary = black in theme OR leave & override later
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                )
            }
        }
    }
}
