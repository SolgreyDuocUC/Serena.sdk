package com.duocuc.serena.DAO

import androidx.annotation.IntegerRes
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.duocuc.serena.data.UserData
import com.duocuc.serena.model.UserUiState

interface UserDAO {

    val idTable: IntegerRes;

    //CRUD interacción

    //Listar todos los usuarios (READ)
    @Query("SELECT * FROM usuarios")
    fun getAll(): List<UserData>

    //Buscar por ID
    @Query("SELECT * FROM usuarios WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserData>

    @Query("SELECT * FROM usuarios WHERE nombreUsuario LIKE :nombreUsuario AND " +
            "nombreUsuario LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): UserUiState

    @Insert
    fun insertAll(vararg users: UserData)

    @Delete
    fun delete(user: UserData)

    //Agregar Consultas Personalizadas

}