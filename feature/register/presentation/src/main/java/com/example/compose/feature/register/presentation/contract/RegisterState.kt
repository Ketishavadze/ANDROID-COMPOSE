package com.example.compose.feature.register.presentation.contract

import com.example.compose.feature.register.domain.model.RegisterConfig


data class RegisterState(
    val isLoading: Boolean = false,
    val config: RegisterConfig = emptyList(),
    val values: Map<Int, String> = emptyMap()
)