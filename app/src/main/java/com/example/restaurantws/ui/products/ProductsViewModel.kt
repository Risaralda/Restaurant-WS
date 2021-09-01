package com.example.restaurantws.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.data.main.models.categorias.Category
import com.example.restaurantws.data.main.models.products.Product
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductsViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _productsResult = MutableStateFlow<Resource<List<Product>>>(Resource.Empty())
    val signUpResult: StateFlow<Resource<List<Product>>> = _productsResult

    fun loadProducts(categoryId: Int) {
        viewModelScope.launch {
            mainRepository.getProductsByCategory(categoryId)
                .onStart { _productsResult.value = Resource.Loading() }
                .catch { _productsResult.value = Resource.Error(it) }
                .collect { _productsResult.value = Resource.Success(it) }
        }
    }
}