package com.example.restaurantws.ui.pedido_detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantws.data.main.models.products.Product
import com.example.restaurantws.databinding.ItemPedidoDetailBinding
import com.example.restaurantws.utils.load

class PedidoDetailAdapter(
    private var items: List<Product>,
    private val onClickItem: (Product) -> Unit
) :
    RecyclerView.Adapter<PedidoDetailAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPedidoDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding)
        {
            pedidoProductQuantity.text = product.quantity.toString()
            "$ ${product.precio}".also { productPriceUnd.text = it }
            "$ ${product.quantity * product.precio}".also { productDetailProductTotal.text = it }

            imgPedidoDetail.load(
                product.url_imagen
            )

            root.setOnClickListener {
                onClickItem(product)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPedidoDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }
}