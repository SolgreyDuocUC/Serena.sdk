package com.duocuc.serena.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "RegistroEmocionalUsuario")
data class EmotionalRegisterData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idEmocion: Int,
    val fecha: LocalDate
)
