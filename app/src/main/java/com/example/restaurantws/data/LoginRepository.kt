package com.example.restaurantws.data

import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.data.auth.models.User
import com.example.restaurantws.data.auth.models.UserDao
import com.example.restaurantws.data.model.LoggedInUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val userDao: UserDao) {
    suspend fun login(loginRequest: LoginRequest) = flow {
        val user = userDao.getUserByEmail(loginRequest.email).first()
        emit(user != null && user.contrasena == loginRequest.pwd)
    }.flowOn(Dispatchers.IO)

    suspend fun signUp(user: User) = flow {
        emit(userDao.insert(user))
    }.flowOn(Dispatchers.IO)

}