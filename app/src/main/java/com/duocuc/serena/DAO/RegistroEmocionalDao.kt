package com.duocuc.serena.DAO

import androidx.room.Dao
import androidx.room.Query
import com.duocuc.serena.data.RegistroEmocionalData

@Dao
interface RegistroEmocionalDao {

    @Query("SELECT * FROM RegistroEmocionalUsuario")
    fun getAll(): List<RegistroEmocionalData>



}