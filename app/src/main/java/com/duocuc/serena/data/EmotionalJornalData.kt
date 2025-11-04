package com.duocuc.serena.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmocionesRegistradas")
class EmotionalJornalData (
    @PrimaryKey (autoGenerate = true)   val idEmocion: Int = 100,
    @ColumnInfo(name = "nombreEmocion") val nombreEmocion: String = "",
    @ColumnInfo(name = "descripcionEmocion") val descripcionEmocion: String = ""
)