package com.example.restaurantws.data.main.models.pedidos

import com.example.restaurantws.data.main.models.products.Product
import com.google.gson.annotations.Expose
import java.util.*

data class Pedido(
    val id: Int,
    val id_cliente: Int,
    val json_pedido: String,
    val total: Int,
    val created_at: Date,
    @Expose(serialize = false)
    val products: List<Product>
)