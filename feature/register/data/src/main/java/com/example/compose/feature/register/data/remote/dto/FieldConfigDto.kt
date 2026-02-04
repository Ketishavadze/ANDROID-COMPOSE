package com.example.compose.feature.register.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FieldConfigDto(
    @SerialName("field_id") val fieldId: Int,
    val hint: String,
    @SerialName("field_type") val fieldType: String,
    val keyboard: String? = null,
    val required: Boolean = false,
    @SerialName("is_active") val isActive: Boolean = true,
    val icon: String? = null
)