package com.example.restaurantws.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restaurantws.data.auth.models.User
import com.example.restaurantws.data.auth.models.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class RestaurantDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}