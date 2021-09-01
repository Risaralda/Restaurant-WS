package com.example.restaurantws.data.auth.models

import com.google.gson.Gson

data class LoginRequest(
    val email: String,
    val pwd: String
) {
    fun toJson(): String = Gson().toJson(this)

    companion object {
        fun fromJson(json: String?): LoginRequest? =
            json?.let { Gson().fromJson(json, LoginRequest::class.java) }
    }
}
