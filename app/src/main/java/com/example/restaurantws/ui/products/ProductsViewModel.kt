package com.example.restaurantws.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.data.main.models.products.Product
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductsViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _productsResult = MutableStateFlow<Resource<List<Product>>>(Resource.Empty())
    val productsResult: StateFlow<Resource<List<Product>>> = _productsResult

    private val _allProductsResult = MutableStateFlow<Resource<List<Product>>>(Resource.Empty())
    val allProductsResult: StateFlow<Resource<List<Product>>> = _allProductsResult

    fun loadProducts(categoryId: Int) {
        if(_productsResult.value is Resource.Success) return

        viewModelScope.launch {
            mainRepository.getProductsByCategory(categoryId)
                .onStart { _productsResult.value = Resource.Loading() }
                .catch { _productsResult.value = Resource.Error(it) }
                .collect { _productsResult.value = Resource.Success(it) }
        }
    }


    fun loadAllProducts() {
        if(_allProductsResult.value is Resource.Success)return

        viewModelScope.launch {
            mainRepository.getProducts()
                .onStart { _allProductsResult.value = Resource.Loading() }
                .catch { _allProductsResult.value = Resource.Error(it) }
                .collect { _allProductsResult.value = Resource.Success(it) }
        }
    }
}