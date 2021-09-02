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

    fun loadPedidos() {
        if(_pedidosResult.value is Resource.Success) return

        viewModelScope.launch {
            mainRepository.getLocalPedidos()
                .onStart { _pedidosResult.value = Resource.Loading() }
                .catch { _pedidosResult.value = Resource.Error(it) }
                .combine(mainRepository.getPedidos()) { a, b ->
                    val finalList = a.toMutableList()
                    finalList.addAll(b)
                    finalList
                }
                .collect { _pedidosResult.value = Resource.Success(it) }
        }
    }
}