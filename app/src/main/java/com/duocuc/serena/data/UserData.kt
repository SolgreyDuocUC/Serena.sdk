package com.duocuc.serena.data

import android.R
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ("usuarios")
class UserData (

    @PrimaryKey(autoGenerate = true)            val id: Int = 100,
    @ColumnInfo (name = "nombreUsuario")        val userName: String?,
    @ColumnInfo (name = "emailUsuario")         val userEmail: String,
    @ColumnInfo (name = "contrseniaUsuario")    val userPassword: String,
    @ColumnInfo (name = "ususarioCondiciones")  val userAceptConditions: Boolean = true

)