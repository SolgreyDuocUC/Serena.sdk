package com.duocuc.serena.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Emociones")
class EmotionsData (
    @PrimaryKey(autoGenerate = true)    val idEntradaEmocional: Int = 1,
    @ColumnInfo(name = "nombreEmocion") val nombreEmocion: String,
    @ColumnInfo(name = "descripcionEmocion") val descripcionEmocion: String
    )