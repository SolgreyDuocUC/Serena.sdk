package com.duocuc.serena.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.duocuc.serena.data.UserData

@Dao
interface UserDAO {

    // Crear usuario (CREATE)
    @Insert
    suspend fun insertUser(user: UserData)

    // Obtener todos los usuarios (READ)
    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsers(): List<UserData>

    // Buscar por IDs (READ)
    @Query("SELECT * FROM usuarios WHERE id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<UserData>

    // Eliminar usuario (DELETE)
    @Delete
    suspend fun deleteUser(user: UserData)

    // Verificar login (opcional, pero muy útil)
    @Query("SELECT * FROM usuarios WHERE emailUsuario = :email AND contraseniaUsuario = :password LIMIT 1")
    suspend fun getUserByCredentials(email: String, password: String): UserData?
}
