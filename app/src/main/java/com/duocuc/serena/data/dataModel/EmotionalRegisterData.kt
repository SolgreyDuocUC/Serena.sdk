package com.duocuc.serena.data.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "RegistroEmocionalUsuario")
data class EmotionalRegisterData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idEmocion: Int,
    val fecha: LocalDate,
    val emocionNombre: String = "",
    val emocionEmoji: String = "",
    val descripcion: String = ""
)


