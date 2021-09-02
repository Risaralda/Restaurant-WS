package com.example.restaurantws.ui.pedidos.adapter

import com.example.restaurantws.data.main.models.pedidos.Pedido

interface PedidosAdapterCallback {
    fun sendPedido(pedido: Pedido)
    fun deletePedido(pedido: Pedido, position: Int)
    fun onClickPedido(pedido: Pedido)
}