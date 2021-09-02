package com.example.restaurantws.data.main.models.products

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Product(
    var id: Int?,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val url_imagen: String,
    @Expose(serialize = false)
    var quantity: Int,
) : Parcelable {
    fun getJsonPedido(): String =
        "{\"id_producto\": $id, \"cantidad\": $quantity, \"precio\": $precio}"
}