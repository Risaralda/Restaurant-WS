package com.example.restaurantws.ui.pedidos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.data.main.models.pedidos.Pedido
import com.example.restaurantws.databinding.ItemPedidosBinding

class PedidosAdapter(private var items: List<Pedido>) :
    RecyclerView.Adapter<PedidosAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPedidosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pedido: Pedido) = with(binding)
        {
            "Pedido # ${pedido.id}".also { pedidoTitle.text = it }

            "Pedido realizado el ${pedido.created_at} por ${CurrentUser.nombre}".also {
                pedidoDesc.text = it
            }
            "Total: ${pedido.total}".also {
                pedidoPrice.text = it
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPedidosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Pedido>) {
        items = newItems
        notifyDataSetChanged()
    }

}