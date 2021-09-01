package com.example.restaurantws.data.main.models.categorias

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val url_imagen: Int,
): Parcelable
