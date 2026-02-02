package com.example.compose.domain.repository


import com.example.compose.common.Resource
import com.example.compose.domain.model.RegisterConfig
import com.example.compose.domain.model.RegisterPayload

interface RegisterRepository {
    suspend fun getRegisterConfig(): Resource<RegisterConfig>
    suspend fun submit(payload: RegisterPayload): Resource<Unit>
}
