package com.example.restaurantws.data.auth.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users where correo = :email")
    fun getUserByEmail(email: String): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(users: User)
}