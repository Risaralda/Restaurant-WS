package com.example.restaurantws.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.core.Resource
import com.example.restaurantws.core.api.ApiService
import com.example.restaurantws.core.api.models.ApiResponse
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.data.main.models.pedidos.Pedido
import com.example.restaurantws.data.main.models.products.Product
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _pedidoResult = MutableStateFlow<Resource<Pedido>>(Resource.Empty())

    private val _insertPedidoResult = MutableStateFlow<Resource<Pedido>>(Resource.Empty())
    val savePedidoResult: StateFlow<Resource<Pedido>> = _insertPedidoResult

    private val _deletePedidoResult = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val deletePedidoResult: StateFlow<Resource<Unit>> = _deletePedidoResult

    private val _enviarPedidoResult = MutableStateFlow<Resource<ApiResponse>>(Resource.Empty())
    val enviarPedidoResult: StateFlow<Resource<ApiResponse>> = _enviarPedidoResult

    fun loadPedido() {
        viewModelScope.launch {
            mainRepository.getOne()
                .onStart { _pedidoResult.value = Resource.Loading() }
                .catch { _pedidoResult.value = Resource.Error(it) }
                .collect { _pedidoResult.value = Resource.Success(it ?: buildNewPedido()) }
        }
    }

    private fun buildNewPedido(): Pedido {
        return Pedido(
            null,
            CurrentUser.idCliente,
            json_pedido = "", 0, Date(), true, mutableListOf()
        )
    }

    fun savePedidoWithProduct(product: Product) {
        val pedido = _pedidoResult.value.data!!
        val index = pedido.products.indexOfFirst { it.id == product.id }
        val finalProducts = pedido.products.toMutableList()

        if (index != -1) {
            if (product.quantity == 0) {
                finalProducts.removeAt(index)
            } else {
                finalProducts[index].quantity = product.quantity
            }
        } else {
            finalProducts.add(product)
        }

        val total = finalProducts.sumOf { it.precio * it.quantity }

        val pedidoToSave = pedido.copy(total = total, products = finalProducts)

        viewModelScope.launch {
            mainRepository.savePedido(pedidoToSave)
                .onStart { _insertPedidoResult.value = Resource.Loading() }
                .catch { _insertPedidoResult.value = Resource.Error(it) }
                .collect { _insertPedidoResult.value = Resource.Success(it) }
        }
    }

    fun deletePedido(pedido: Pedido) {
        viewModelScope.launch {
            mainRepository.deletePedido(pedido)
                .onStart { _deletePedidoResult.value = Resource.Loading() }
                .catch { _deletePedidoResult.value = Resource.Error(it) }
                .collect { _deletePedidoResult.value = Resource.Success(it) }
        }
    }

    fun enviarPedido(pedido: Pedido) {
        viewModelScope.launch {
            mainRepository.enviarPedido(pedido.apply { setJsonPedido() })
                .onStart { _enviarPedidoResult.value = Resource.Loading() }
                .catch { _enviarPedidoResult.value = Resource.Error(it) }
                .collect { _enviarPedidoResult.value = Resource.Success(it) }
        }
    }


}