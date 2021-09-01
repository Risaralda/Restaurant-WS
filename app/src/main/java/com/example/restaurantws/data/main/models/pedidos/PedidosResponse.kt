package com.example.restaurantws.data.main.models.pedidos

data class PedidosResponse(
    val respuesta: String,
    val pedidos: List<Pedido>
)