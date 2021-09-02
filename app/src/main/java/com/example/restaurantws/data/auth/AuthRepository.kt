package com.example.restaurantws.data.auth

import com.example.restaurantws.core.api.ApiService
import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.data.auth.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(
    private val apiService: ApiService
) {
    suspend fun login(loginRequest: LoginRequest) = flow {
        emit(apiService.login(loginRequest))
    }.flowOn(Dispatchers.IO)

    suspend fun signUp(user: User) = flow {
        emit(apiService.signUp(user))
    }.flowOn(Dispatchers.IO)


    suspend fun getPrivacyPolicies() = flow {
        emit(apiService.getPrivacyPolicies().datos)
    }.flowOn(Dispatchers.IO)
}