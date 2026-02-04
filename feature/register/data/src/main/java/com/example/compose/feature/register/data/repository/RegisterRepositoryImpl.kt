package com.example.compose.feature.register.data.repository


import com.example.compose.core.domain.common.Resource
import com.example.compose.feature.register.data.mapper.toDomain
import com.example.compose.feature.register.data.remote.api.RegisterApiService
import com.example.compose.feature.register.domain.model.FieldType
import com.example.compose.feature.register.domain.model.KeyboardType
import com.example.compose.feature.register.domain.model.RegisterConfig
import com.example.compose.feature.register.domain.model.RegisterPayload
import com.example.compose.feature.register.domain.repository.RegisterRepository
import javax.inject.Inject
import kotlin.collections.map

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
            com.example.compose.feature.register.domain.model.FieldConfig(
                fieldId = 1, hint = "UserName",
                fieldType = FieldType.INPUT,
                keyboard = KeyboardType.TEXT,
                required = false, isActive = true, iconUrl = null
            ),
            com.example.compose.feature.register.domain.model.FieldConfig(
                fieldId = 2, hint = "Email",
                fieldType = FieldType.INPUT,
                keyboard = KeyboardType.TEXT,
                required = true, isActive = true, iconUrl = null
            ),
            com.example.compose.feature.register.domain.model.FieldConfig(
                fieldId = 3, hint = "Phone",
                fieldType = FieldType.INPUT,
                keyboard = KeyboardType.NUMBER,
                required = true, isActive = true, iconUrl = null
            )
        ),
        listOf(
            com.example.compose.feature.register.domain.model.FieldConfig(
                fieldId = 4, hint = "FullName",
                fieldType = FieldType.INPUT,
                keyboard = KeyboardType.TEXT,
                required = true, isActive = true, iconUrl = null
            ),
            com.example.compose.feature.register.domain.model.FieldConfig(
                fieldId = 89, hint = "Birthday",
                fieldType = FieldType.CHOOSER,
                keyboard = null,
                required = false, isActive = true, iconUrl = null
            ),
            com.example.compose.feature.register.domain.model.FieldConfig(
                fieldId = 898, hint = "Gender",
                fieldType = FieldType.CHOOSER,
                keyboard = null,
                required = false, isActive = true, iconUrl = null
            )
        )
    )
}
