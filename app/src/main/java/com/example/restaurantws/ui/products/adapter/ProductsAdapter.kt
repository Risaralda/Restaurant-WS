package com.example.restaurantws.ui.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantws.data.main.models.products.Product
import com.example.restaurantws.databinding.ItemProductsBinding
import com.example.restaurantws.utils.loadDrawable

class ProductsAdapter(private var items: MutableList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding)
        {
            productName.text = product.nombre
            productDesc.text = product.descripcion
            "$ ${product.precio}".also { productPrice.text = it }


            productQuantity.apply {
                isVisible = product.quantity > 0
                text = product.quantity.toString()
            }

            imgRemoveFromCart.apply {
                isVisible = product.quantity > 0
            }

            imgProduct.loadDrawable(
                product.url_imagen
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Product>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

}