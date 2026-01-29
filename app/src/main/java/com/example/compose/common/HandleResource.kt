package com.example.compose.common

import retrofit2.HttpException
import java.io.IOException

class HandleResource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            val response = apiCall()
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error(e.message() ?: "An HTTP error occurred")
        } catch (e: IOException) {
            Resource.Error("Network error. Please check your connection.")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unexpected error occurred")
        }
    }
}