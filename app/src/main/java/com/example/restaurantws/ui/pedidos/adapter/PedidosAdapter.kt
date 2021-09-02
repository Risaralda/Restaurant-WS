package com.example.restaurantws.ui.pedidos.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.data.main.models.pedidos.Pedido
import com.example.restaurantws.databinding.ItemPedidosBinding
import java.text.SimpleDateFormat
import java.util.*

class PedidosAdapter(
    private var items: MutableList<Pedido>,
    private val pedidosAdapterCallback: PedidosAdapterCallback
) :
    RecyclerView.Adapter<PedidosAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPedidosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = with(binding)
        {
            val pedido = items[position]

            if (pedido.isLocal) {
                "Pedido en proceso".also { pedidoTitle.text = it }
                "Pedido iniciado el ${formatDate(pedido.created_at)} por ${CurrentUser.nombre}".also {
                    pedidoDesc.text = it
                }
            } else {
                "Pedido #${pedido.id}".also { pedidoTitle.text = it }
                "Pedido creado el ${formatDate(pedido.created_at)} por ${CurrentUser.nombre}".also {
                    pedidoDesc.text = it
                }
            }

            "Total: $ ${pedido.total}".also {
                pedidoPrice.text = it
            }


            if (pedido.isLocal) {

                imgSendPedido.setOnClickListener { pedidosAdapterCallback.sendPedido(pedido) }
                imgDeletePedido.setOnClickListener {
                    pedidosAdapterCallback.deletePedido(
                        pedido,
                        position
                    )
                }
            } else {
                imgSendPedido.isVisible = false
                imgDeletePedido.isVisible = false
            }

            root.setOnClickListener{
                pedidosAdapterCallback.onClickPedido(pedido)
            }
        }
    }


    private fun formatDate(date: Date): String {
        val format = "yyyy-MM-dd HH:mm aa"
        return SimpleDateFormat(format, Locale.getDefault()).format(date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPedidosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<Pedido>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun deleteItem(pedido: Pedido, position: Int) {
        items[position] = pedido
        notifyItemRemoved(position)
    }

}