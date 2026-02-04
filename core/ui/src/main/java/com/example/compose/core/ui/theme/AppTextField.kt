package com.example.compose.core.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colorScheme.surfaceVariant,
    textColor: Color = AppTheme.colorScheme.onSurface,
    hintColor: Color = AppTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    textStyle: TextStyle = AppTheme.typography.bodyMedium
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor, RoundedCornerShape(12.dp)),
        textStyle = textStyle.copy(color = textColor),
        placeholder = {
            Text(
                text = hint,
                style = textStyle,
                color = hintColor
            )
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            cursorColor = AppTheme.colorScheme.primary
        )
    )
}
