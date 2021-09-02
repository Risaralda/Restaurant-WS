package com.example.restaurantws.ui.products.adapter

import com.example.restaurantws.data.main.models.products.Product

interface ProductsAdapterCallback {
    fun onAddItemToCart(product: Product, position: Int)
    fun onRemoveItemFromCart(product: Product, position: Int)
    fun onClickItem(product: Product)
}