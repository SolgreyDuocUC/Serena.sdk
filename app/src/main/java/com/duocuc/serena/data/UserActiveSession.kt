package com.duocuc.serena.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SesionActiva")
data class UserActiveSession(

    @PrimaryKey
    val sessionId: Int = 1,

    @ColumnInfo(name = "id_usuario_activo")
    val activeUserId: Int
)