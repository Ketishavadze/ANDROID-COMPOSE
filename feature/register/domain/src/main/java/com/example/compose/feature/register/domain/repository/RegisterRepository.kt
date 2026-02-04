package com.example.compose.feature.register.domain.repository

import com.example.compose.core.domain.common.Resource
import com.example.compose.feature.register.domain.model.RegisterConfig
import com.example.compose.feature.register.domain.model.RegisterPayload

interface RegisterRepository {
    suspend fun getRegisterConfig(): Resource<RegisterConfig>
    suspend fun submit(payload: RegisterPayload): Resource<Unit>
}