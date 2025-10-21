package com.duocuc.serena.DAO

import androidx.annotation.IntegerRes
import androidx.room.Query
import com.duocuc.serena.data.UserData

interface UserDAO {

    val idTable: IntegerRes;

    //CRUD interacción

    //Listar todos los usuarios (READ)
    @Query("SELECT * FROM usuarios")
    fun getAll(): List<UserData>


    //Agregar Consultas Personalizadas

}