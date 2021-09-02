package com.example.restaurantws.data.auth.models

data class LoginResponse(
    val respuesta: String,
    val nombre: String,
    val idCliente: Int,
    val token: String,

    )
