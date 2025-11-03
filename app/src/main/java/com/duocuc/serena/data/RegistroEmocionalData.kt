package com.duocuc.serena.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RegistroEmocionalUsuario")
class RegistroEmocionalData (

    @PrimaryKey(autoGenerate = true)    val idEntradaEmocional: Int = 1,
    @ColumnInfo(name = "idEmocion")     val idEmcoion: Int

)