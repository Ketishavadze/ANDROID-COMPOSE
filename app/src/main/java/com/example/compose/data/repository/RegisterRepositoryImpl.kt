package com.example.compose.data.repository

import com.example.compose.common.Resource
import com.example.compose.data.mapper.toDomain
import com.example.compose.data.remote.api.RegisterApiService
import com.example.compose.domain.model.RegisterConfig
import com.example.compose.domain.model.RegisterPayload
import com.example.compose.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApiService?
) : RegisterRepository {

    override suspend fun getRegisterConfig(): Resource<RegisterConfig> {
        return try {
            if (api == null) return Resource.Success(FakeRegisterConfig.config)

            val dto = api.getConfig()
            Resource.Success(dto.map { group -> group.map { it.toDomain() } })
        } catch (t: Throwable) {
            Resource.Success(FakeRegisterConfig.config)
        }
    }

    override suspend fun submit(payload: RegisterPayload): Resource<Unit> {
        return Resource.Success(Unit)
    }
}

object FakeRegisterConfig {
    val config: RegisterConfig = listOf(
        listOf(
            com.example.compose.domain.model.FieldConfig(
                fieldId = 1, hint = "UserName",
                fieldType = com.example.compose.domain.model.FieldType.INPUT,
                keyboard = com.example.compose.domain.model.KeyboardType.TEXT,
                required = false, isActive = true, iconUrl = null
            ),
            com.example.compose.domain.model.FieldConfig(
                fieldId = 2, hint = "Email",
                fieldType = com.example.compose.domain.model.FieldType.INPUT,
                keyboard = com.example.compose.domain.model.KeyboardType.TEXT,
                required = true, isActive = true, iconUrl = null
            ),
            com.example.compose.domain.model.FieldConfig(
                fieldId = 3, hint = "Phone",
                fieldType = com.example.compose.domain.model.FieldType.INPUT,
                keyboard = com.example.compose.domain.model.KeyboardType.NUMBER,
                required = true, isActive = true, iconUrl = null
            )
        ),
        listOf(
            com.example.compose.domain.model.FieldConfig(
                fieldId = 4, hint = "FullName",
                fieldType = com.example.compose.domain.model.FieldType.INPUT,
                keyboard = com.example.compose.domain.model.KeyboardType.TEXT,
                required = true, isActive = true, iconUrl = null
            ),
            com.example.compose.domain.model.FieldConfig(
                fieldId = 89, hint = "Birthday",
                fieldType = com.example.compose.domain.model.FieldType.CHOOSER,
                keyboard = null,
                required = false, isActive = true, iconUrl = null
            ),
            com.example.compose.domain.model.FieldConfig(
                fieldId = 898, hint = "Gender",
                fieldType = com.example.compose.domain.model.FieldType.CHOOSER,
                keyboard = null,
                required = false, isActive = true, iconUrl = null
            )
        )
    )
}
