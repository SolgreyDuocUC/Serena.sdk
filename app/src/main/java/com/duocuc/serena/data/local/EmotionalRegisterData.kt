package com.duocuc.serena.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "emotional_registers")
data class EmotionalRegisterData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idEmocion: Int,
    val descripcion: String,
    val fecha: LocalDate
)