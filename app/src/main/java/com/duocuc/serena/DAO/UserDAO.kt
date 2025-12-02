package com.duocuc.serena.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.duocuc.serena.data.dataModel.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun findUserByIdFlow(id: Int): Flow<UserData?>

    @Query("SELECT * FROM usuarios WHERE emailUsuario = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserData?

    @Insert
    suspend fun insertAll(vararg users: UserData)

    @Query("SELECT * FROM usuarios WHERE emailUsuario = :email AND contrseniaUsuario = :password LIMIT 1")
    suspend fun findByEmailAndPassword(email: String, password: String): UserData?

    @Update
    suspend fun updateUser(user: UserData)
}