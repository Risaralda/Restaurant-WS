package com.example.restaurantws.data.auth.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val ciudad: String
)