package com.example.restaurantws.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.restaurantws.data.main.PedidosDao
import com.example.restaurantws.data.main.models.pedidos.Pedido

@Database(entities = [Pedido::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RestaurantDB : RoomDatabase() {
    abstract fun pedidosDao(): PedidosDao
}