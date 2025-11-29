package com.duocuc.serena.data.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("usuarios")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nombreUsuario")
    val userName: String?,
    @ColumnInfo(name = "emailUsuario")
    val userEmail: String,
    @ColumnInfo(name = "contrseniaUsuario")
    val userPassword: String,
    @ColumnInfo(name = "usuarioCondiciones")
    val userAceptConditions: Boolean = true,
    @ColumnInfo(name = "imagenUsuario")
    val userImageUri: String? = null
)