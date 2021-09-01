package com.example.restaurantws.data.main.models.products

import com.google.gson.annotations.Expose
import java.util.*

data class Product(
    var id: Int?,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val url_imagen: Int,
    @Expose(serialize = false)
    val createAt: Date,
    @Expose(serialize = false)
    val isLocal: Boolean = true,
    @Expose(serialize = false)
    var quantity: Int,
)