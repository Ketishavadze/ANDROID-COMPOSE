package com.example.compose.feature.register.domain.model

data class FieldConfig(
    val fieldId: Int,
    val hint: String,
    val fieldType: FieldType,
    val keyboard: KeyboardType?,
    val required: Boolean,
    val isActive: Boolean,
    val iconUrl: String?
)

typealias FieldGroup = List<FieldConfig>
typealias RegisterConfig = List<FieldGroup>