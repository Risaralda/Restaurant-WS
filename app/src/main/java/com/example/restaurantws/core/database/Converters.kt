package com.example.restaurantws.core.database

import androidx.room.TypeConverter
import com.example.restaurantws.data.main.models.products.Product
import com.google.gson.Gson
import java.util.*

class Converters {
    @TypeConverter
    fun toDate(time: Long): Date = Date(time)

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toProducts(json: String): List<Product> =
        Gson().fromJson(json, Array<Product>::class.java).toList()

    @TypeConverter
    fun fromProducts(products: List<Product>): String = Gson().toJson(products)


}