package com.duocuc.serena.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.duocuc.serena.data.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    //CRUD interacción

    //Listar todos los usuarios (READ)
    @Query("SELECT * FROM usuarios")
    fun getAll(): List<UserData>

    //Buscar por ID
    @Query("SELECT * FROM usuarios WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserData>

    @Query("SELECT * FROM usuarios WHERE id = :userId")
    fun findUserByIdFlow(userId: Int): Flow<UserData?>

    @Query("SELECT * FROM usuarios WHERE emailUsuario = :email LIMIT 1")
    fun findByEmail(email: String): UserData?

    @Query("SELECT * FROM usuarios WHERE emailUsuario = :email AND contrseniaUsuario = :password LIMIT 1")
    fun findByEmailAndPassword(email: String, password: String): UserData?

    @Insert
    fun insertAll(vararg users: UserData)

    @Update
    suspend fun updateUser(user: UserData)

    @Delete
    fun delete(user: UserData)


}