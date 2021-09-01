package com.example.restaurantws.data.auth

import android.content.SharedPreferences
import com.example.restaurantws.data.auth.models.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(
    private val sharedPreferences: SharedPreferences
) {
    suspend fun login(loginRequest: LoginRequest) = flow {
//        val user = userDao.getUserByEmail(loginRequest.email).first()
        val user =
            Gson().fromJson(sharedPreferences.getString(loginRequest.email, null), User::class.java)
        if (user != null && user.contrasena == loginRequest.pwd) {
            emit(user)
        } else {
            throw Exception("Usuario no válido, revise sus credenciales e intente nuevamente")
        }
    }.flowOn(Dispatchers.IO)

    suspend fun signUp(user: User) = flow {
        with(sharedPreferences.edit()) {
            putString(user.correo, Gson().toJson(user))
            commit()
        }

        emit(Unit)
    }.flowOn(Dispatchers.IO)


    suspend fun getPrivacyPolicies() = flow {
        emit(
            Gson().fromJson(
                sharedPreferences.getString("saved_policies", null),
                PoliciesResponse::class.java
            ).datos
        )
    }.flowOn(Dispatchers.IO)


}