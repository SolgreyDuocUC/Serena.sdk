package com.duocuc.serena.data.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SesionActiva")
data class UserActiveSession(

    @PrimaryKey
    val sessionId: Long = 1,

    @ColumnInfo(name = "id_usuario_activo")
    val activeUserId: Long
)