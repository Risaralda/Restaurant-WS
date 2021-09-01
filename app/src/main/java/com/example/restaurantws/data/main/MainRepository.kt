package com.example.restaurantws.data.main

import android.content.SharedPreferences
import com.example.restaurantws.data.main.models.categorias.CategoryResponse
import com.example.restaurantws.data.main.models.especialidad.SpecialityResponse
import com.example.restaurantws.data.main.models.products.ProductsResponse
import com.example.restaurantws.data.main.models.pedidos.PedidosResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepository(private val sharedPreferences: SharedPreferences) {
    suspend fun getSpeciality() = flow {
        emit(
            Gson().fromJson(
                sharedPreferences.getString("saved_especialidad", null),
                SpecialityResponse::class.java
            ).datos
        )
    }.flowOn(Dispatchers.IO)

    suspend fun getCategories() = flow {
        emit(
            Gson().fromJson(
                sharedPreferences.getString("saved_categories", null),
                CategoryResponse::class.java
            ).datos
        )
    }.flowOn(Dispatchers.IO)

    suspend fun getProductsByCategory(categoryId: Int) = flow {
        val products = Gson().fromJson(
            sharedPreferences.getString("saved_products", null),
            ProductsResponse::class.java
        ).productos

        when (categoryId) {
            1 -> emit(products.filter { it.id in 1..5 })
            2 -> emit(products.filter { it.id in 6..11 })
            3 -> emit(products.filter { it.id in 12..15 })
            4 -> emit(products.filter { it.id in 16..18 || it.id == 13 })
        }

    }.flowOn(Dispatchers.IO)


    suspend fun getPedidos() = flow {
        emit(
            Gson().fromJson(
                sharedPreferences.getString("saved_pedidos", null),
                PedidosResponse::class.java
            ).pedidos
        )
    }.flowOn(Dispatchers.IO)

}