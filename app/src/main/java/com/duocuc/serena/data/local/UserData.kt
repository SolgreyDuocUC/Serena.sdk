package com.duocuc.serena.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userEmail: String,
    val userPassword: String,
    val userName: String?,
    val image: String? = null
)