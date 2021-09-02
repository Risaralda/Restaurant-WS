package com.example.restaurantws.data.main

import com.example.restaurantws.core.api.ApiService
import com.example.restaurantws.data.main.models.pedidos.Pedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepository(private val apiService: ApiService, private val pedidosDao: PedidosDao) {
    fun getLocalPedidos() = pedidosDao.getAllPedidos()
    fun getOne() = pedidosDao.getOnePedido()

    suspend fun getSpeciality() = flow {
        emit(
            apiService.getSpeciality().datos
        )
    }.flowOn(Dispatchers.IO)

    suspend fun getCategories() = flow {
        emit(
            apiService.getCategorias().datos
        )
    }.flowOn(Dispatchers.IO)

    suspend fun getProductsByCategory(categoryId: Int) = flow {
        val resp = apiService.getProductosByCategory(categoryId).productos
        val pedido = pedidosDao.getOnePedido().first()

        if (pedido != null) {
            resp.forEach { prod ->
                val index = pedido.products.indexOfFirst { it.id == prod.id }

                if (index != -1) {
                    prod.quantity = pedido.products[index].quantity
                }
            }
        }

        emit(resp)
    }.flowOn(Dispatchers.IO)


    suspend fun savePedido(pedido: Pedido) = flow {
        if (pedido.id == null) {
            val id = pedidosDao.insert(pedido)
            pedido.id = id.toInt()
        } else {
            pedidosDao.update(pedido)
        }

        emit(pedido)
    }

    suspend fun deletePedido(pedido: Pedido) = flow {
        emit(pedidosDao.deleteById(pedido.id!!))
    }

    suspend fun enviarPedido(pedido: Pedido) = flow {
        val response = apiService.enviarPedido(pedido.copy(id = null))
        pedidosDao.deleteById(pedido.id!!)
        emit(response)
    }


    suspend fun getPedidos() = flow {
        emit(
            apiService.getPedidos().pedidos.reversed()
        )
    }.flowOn(Dispatchers.IO)

    suspend fun getProducts() = flow {
        emit(apiService.getAllProducts().productos)
    }.flowOn(Dispatchers.IO)

}