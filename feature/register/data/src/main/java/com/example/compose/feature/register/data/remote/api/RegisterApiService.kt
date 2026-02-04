package com.example.compose.feature.register.data.remote.api

import com.example.compose.feature.register.data.remote.dto.FieldConfigDto
import retrofit2.http.GET

interface RegisterApiService {
    @GET("register/config")
    suspend fun getConfig(): List<List<FieldConfigDto>>

    @GET("register/submit")
    suspend fun submit(): Unit
}
