package com.example.restaurantws.data.main.models.pedidos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.restaurantws.data.main.models.products.Product
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "pedidos")
@Parcelize
data class Pedido(
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false)
    var id: Int?,
    val id_cliente: Int,
    var json_pedido: String,
    val total: Int,
    @Expose(serialize = false)
    val created_at: Date,
    @Expose(serialize = false)
    val isLocal: Boolean = true,
    @Expose(serialize = false)
    val products: List<Product> = listOf(),
) : Parcelable {
    fun setJsonPedido() {
        json_pedido = Gson().toJson(products.map { it.getJsonPedido() })
    }
}