package com.duocuc.serena.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "RegistroEmocionalUsuario")
data class EmotionalRegisterData (
    @PrimaryKey(autoGenerate = true)
    val idEntradaEmocional: Int = 0,

    @ColumnInfo(name = "idEmocion")
    val idEmocion: Int,

    @ColumnInfo(name = "fecha")
    val fecha: LocalDate
)