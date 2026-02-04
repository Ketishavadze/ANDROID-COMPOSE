package com.example.compose.feature.register.data.mapper


import com.example.compose.feature.register.data.remote.dto.FieldConfigDto
import com.example.compose.feature.register.domain.model.FieldConfig
import com.example.compose.feature.register.domain.model.FieldType
import com.example.compose.feature.register.domain.model.KeyboardType

fun FieldConfigDto.toDomain(): FieldConfig {
    val type = when (fieldType.lowercase()) {
        "input" -> FieldType.INPUT
        "chooser" -> FieldType.CHOOSER
        else -> FieldType.INPUT
    }
    val kb = when (keyboard?.lowercase()) {
        "text" -> KeyboardType.TEXT
        "number" -> KeyboardType.NUMBER
        else -> null
    }
    return FieldConfig(
        fieldId = fieldId,
        hint = hint,
        fieldType = type,
        keyboard = kb,
        required = required,
        isActive = isActive,
        iconUrl = icon
    )
}
