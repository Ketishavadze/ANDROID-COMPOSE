package com.example.compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.domain.model.OrderStatus
import com.example.compose.ui.graphics.AppColors

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
                            color = if (isSelected) AppColors.ChipSelectedText else AppColors.ChipUnselectedText
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppColors.ChipSelectedBg,
                        containerColor = AppColors.ChipUnselectedBg
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) AppColors.ChipSelectedBg else AppColors.ChipBorder
                    )
                )
            }
        }
    }
}
