package com.duocuc.serena.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nombreUsuario")
    val userName: String?,

    @ColumnInfo(name = "emailUsuario")
    val userEmail: String,

    @ColumnInfo(name = "contraseniaUsuario") // corregido: “contraseña”
    val userPassword: String,

    @ColumnInfo(name = "usuarioCondiciones") // corregido: “usuario”
    val userAceptConditions: Boolean = true
)
