package com.example.restaurantws.ui.pedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.data.main.models.pedidos.Pedido
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PedidosViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val _pedidosResult = MutableStateFlow<Resource<List<Pedido>>>(Resource.Empty())
    val signUpResult: StateFlow<Resource<List<Pedido>>> = _pedidosResult

    fun loadProducts() {
        viewModelScope.launch {
            mainRepository.getPedidos()
                .onStart { _pedidosResult.value = Resource.Loading() }
                .catch { _pedidosResult.value = Resource.Error(it) }
                .collect { _pedidosResult.value = Resource.Success(it) }
        }
    }
}