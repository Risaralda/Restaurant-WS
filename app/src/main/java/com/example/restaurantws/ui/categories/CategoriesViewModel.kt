package com.example.restaurantws.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.MainRepository
import com.example.restaurantws.data.main.models.categorias.Category
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CategoriesViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _categoriesResult = MutableStateFlow<Resource<List<Category>>>(Resource.Empty())
    val signUpResult: StateFlow<Resource<List<Category>>> = _categoriesResult

    fun loadCategories() {
        if(_categoriesResult.value is Resource.Success) return

        viewModelScope.launch {
            mainRepository.getCategories()
                .onStart { _categoriesResult.value = Resource.Loading() }
                .catch { _categoriesResult.value = Resource.Error(it) }
                .collect { _categoriesResult.value = Resource.Success(it) }
        }
    }
}