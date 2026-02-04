package com.example.compose.feature.register.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType as ComposeKeyboardType
import com.example.compose.feature.register.domain.model.KeyboardType as DomainKeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.core.ui.extensions.applyIf
import com.example.compose.core.ui.theme.AppTheme
import com.example.compose.feature.register.domain.model.FieldType
import com.example.compose.feature.register.presentation.contract.RegisterEvent
import kotlin.collections.forEachIndexed

@Composable
fun RegisterScreen(
    state: com.example.compose.feature.register.presentation.contract.RegisterState,
    message: String?,
    onEvent: (RegisterEvent) -> Unit
) {
    val c = AppTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
            .padding(16.dp)
    ) {
        Column(Modifier.fillMaxSize()) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(28.dp)
                        .background(c.surfaceVariant, RoundedCornerShape(8.dp))
                )
                Spacer(Modifier.width(8.dp))
                com.example.compose.core.ui.theme.AppText(
                    "E AUTH",
                    style = AppTheme.typography.titleMedium,
                    color = c.onBackground
                )
                Spacer(Modifier.weight(1f))
                com.example.compose.core.ui.theme.AppText(
                    "Register",
                    style = AppTheme.typography.titleMedium,
                    color = c.primary
                )
            }

            Spacer(Modifier.height(24.dp))

            state.config.forEach { group ->
                FieldGroupCard(
                    modifier = Modifier.fillMaxWidth(),
                    group = group.filter { it.isActive },
                    values = state.values,
                    onValueChange = { id, v -> onEvent(RegisterEvent.OnValueChange(id, v)) }
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.weight(1f))

            Spacer(Modifier.Companion.applyIf(message != null) { height(10.dp) })

            if (message != null) {
                com.example.compose.core.ui.theme.AppText(
                    message,
                    style = AppTheme.typography.bodyMedium,
                    color = c.error
                )
                Spacer(Modifier.height(10.dp))
            }

            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(c.primary, RoundedCornerShape(24.dp))
                    .clickable { onEvent(RegisterEvent.Submit) }
                    .padding(horizontal = 18.dp, vertical = 12.dp)
            ) {
                com.example.compose.core.ui.theme.AppText(
                    "Register →",
                    style = AppTheme.typography.titleSmall,
                    color = c.onPrimary
                )
            }
        }

        Box(
            Modifier
                .fillMaxSize()
                .applyIf(state.isLoading) {
                    background(c.background.copy(alpha = 0.5f))
                }
                .applyIf(!state.isLoading) {
                    size(0.dp)
                },
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun FieldGroupCard(
    modifier: Modifier,
    group: List<com.example.compose.feature.register.domain.model.FieldConfig>,
    values: Map<Int, String>,
    onValueChange: (Int, String) -> Unit
) {
    val c = AppTheme.colorScheme
    Column(
        modifier = modifier
            .background(c.surface, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        group.forEachIndexed { idx, field ->
            when (field.fieldType) {
                FieldType.INPUT -> {
                    AppInputRow(
                        hint = field.hint,
                        value = values[field.fieldId].orEmpty(),
                        keyboard = field.keyboard,
                        onValueChange = { onValueChange(field.fieldId, it) }
                    )
                }
                FieldType.CHOOSER -> {
                    AppChooserRow(
                        hint = field.hint,
                        value = values[field.fieldId].orEmpty(),
                        onClick = {
                            onValueChange(field.fieldId, "Selected")
                        }
                    )
                }
            }

            if (idx != group.lastIndex) {
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun AppInputRow(
    hint: String,
    value: String,
    keyboard: DomainKeyboardType?,
    onValueChange: (String) -> Unit
) {
    val c = AppTheme.colorScheme
    com.example.compose.core.ui.theme.AppTextField(
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (keyboard) {
                DomainKeyboardType.NUMBER -> ComposeKeyboardType.Number
                DomainKeyboardType.TEXT -> ComposeKeyboardType.Text
                else -> ComposeKeyboardType.Text
            }
        ),
        containerColor = c.surfaceVariant,
        textColor = c.onSurface
    )
}

@Composable
private fun AppChooserRow(
    hint: String,
    value: String,
    onClick: () -> Unit
) {
    val c = AppTheme.colorScheme
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(c.surfaceVariant, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        com.example.compose.core.ui.theme.AppText(
            text = if (value.isEmpty()) hint else value,
            style = AppTheme.typography.bodyMedium,
            color = if (value.isEmpty()) c.onSurface.copy(alpha = 0.6f) else c.onSurface
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(
            state = com.example.compose.feature.register.presentation.contract.RegisterState(
                config = listOf(
                    listOf(
                        com.example.compose.feature.register.domain.model.FieldConfig(
                            fieldId = 1,
                            hint = "Email",
                            fieldType = FieldType.INPUT,
                            keyboard = DomainKeyboardType.TEXT,
                            required = true,
                            isActive = true,
                            iconUrl = null
                        ),
                        com.example.compose.feature.register.domain.model.FieldConfig(
                            fieldId = 2,
                            hint = "Password",
                            fieldType = FieldType.INPUT,
                            keyboard = DomainKeyboardType.TEXT,
                            required = true,
                            isActive = true,
                            iconUrl = null
                        )
                    ),
                    listOf(
                        com.example.compose.feature.register.domain.model.FieldConfig(
                            fieldId = 3,
                            hint = "Country",
                            fieldType = FieldType.CHOOSER,
                            keyboard = null,
                            required = true,
                            isActive = true,
                            iconUrl = null
                        )
                    )
                ),
                values = emptyMap(),
                isLoading = false
            ),
            message = null,
            onEvent = {}
        )
    }
}