package com.example.restaurantws.data.main

import androidx.room.*
import com.example.restaurantws.data.main.models.pedidos.Pedido
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidosDao {
    @Query("SELECT * FROM pedidos")
    fun getAllPedidos(): Flow<List<Pedido>>

    @Query("SELECT * FROM pedidos LIMIT 1")
    fun getOnePedido(): Flow<Pedido?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(pedido: Pedido): Long

    @Update
    suspend fun update(pedido: Pedido): Int

    @Query("DELETE FROM pedidos where id = :pedidoId")
    suspend fun deleteById(pedidoId: Int)


}